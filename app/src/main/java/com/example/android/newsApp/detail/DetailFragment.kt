package com.example.android.newsApp.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.android.newsApp.databinding.FragmentDetailBinding

/**
 * Uses a web view to display details about the selected article.
 * Gets info about the article through safe args.
 */

class DetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentDetailBinding.inflate(inflater)

        val article = DetailFragmentArgs.fromBundle(requireArguments()).selectedProperty
        val articleWebView = binding.webView
        articleWebView.apply {
            webViewClient= WebViewClient()
            loadUrl(article.url.toString())
        }

        return binding.root
    }
}