package com.jutter.sharerecipes.ui.bs.share

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.arellomobile.mvp.MvpView
import com.jutter.sharerecipes.R
import com.jutter.sharerecipes.common.base.MvpBottomSheetDialogFragment
import kotlinx.android.synthetic.main.bs_share_link.*


class ShareLinkBSFragment : MvpBottomSheetDialogFragment(R.layout.bs_share_link), MvpView {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btnCancel.setOnClickListener { dismiss() }

        btnCopyLink.setOnClickListener{
            val clipboard = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("link" , arguments?.getString(ARG_LINK))
            clipboard.setPrimaryClip(clip)
            Toast.makeText(context, resources.getText(R.string.share_link_bs_success), Toast.LENGTH_SHORT).show()
            dismiss()
        }

        btnSocialShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, arguments?.getString(ARG_LINK))
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
            dismiss()
        }
    }

    companion object{
        private const val ARG_LINK = "arg_link"

        fun create(link: Any): ShareLinkBSFragment {
            val linkString = link as String

            val fragment = ShareLinkBSFragment()

            val args = Bundle()
            args.putString(ARG_LINK, linkString)
            fragment.arguments = args

            return fragment
        }
    }
}