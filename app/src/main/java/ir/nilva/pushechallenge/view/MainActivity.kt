package ir.nilva.pushechallenge.view

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import ir.nilva.pushechallenge.R
import ir.nilva.pushechallenge.utils.MarginItemDecoration
import ir.nilva.pushechallenge.utils.viewModelProvider
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: MainActivityViewModel

    private lateinit var adapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = viewModelProvider(viewModelFactory)

        adapter = MessageAdapter().apply {
            itemClicked += { viewModel.delete(adapter.getItemId(it.position)) }
        }

        recyclerView.addItemDecoration(MarginItemDecoration())
        recyclerView.adapter = adapter

        viewModel.getMessages().observe(this, Observer {
            adapter.submitList(it)
        })

        sendButton.setOnClickListener {
            viewModel.send(contentTextView.text.toString())
        }
    }

}
