package com.example.android.newsApp.overview

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.newsApp.databinding.FragmentOverviewBinding


/**
 * Fragment shows all news articles we have fetched.
 */
class OverviewFragment : Fragment(), View.OnClickListener {

    /**
     * Lazily initialize our [OverviewViewModel].
     */
    private val viewModel: OverviewViewModel by lazy {
        ViewModelProvider(this).get(OverviewViewModel::class.java)
    }

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment,
     * then sets up our RecyclerView.
     */
    @Suppress
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentOverviewBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel


        // Sets the adapter of the photosGrid RecyclerView with clickHandler lambda that
        // tells the viewModel when our property is clicked
        binding.photosGrid.adapter = RVAdapter(RVAdapter.OnClickListener {
            viewModel.showArticle(it)
        })

        // Observe the navigateToArticle LiveData and Navigate when it isn't null
        viewModel.navigateToArticle.observe(viewLifecycleOwner, Observer {
            if ( null != it ) {
                // Find the NavController from the Fragment
                this.findNavController().navigate(OverviewFragmentDirections.actionShowDetail(it))
                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                viewModel.showArticleComplete()
            }
        })

        // Set listener for searchView
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchNews(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        // Set onClickListeners for the buttons
        binding.generalBtn.setOnClickListener(this)
        binding.businessBtn.setOnClickListener(this)
        binding.entertainmentBtn.setOnClickListener(this)
        binding.healthBtn.setOnClickListener(this)
        binding.scienceBtn.setOnClickListener(this)
        binding.sportsBtn.setOnClickListener(this)
        binding.technologyBtn.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(v: View?) {
        val button: Button = v as Button
        val category = button.text.toString()
        viewModel.updateCategory(category.lowercase())
    }
}
