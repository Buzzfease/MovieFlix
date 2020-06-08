package top.hanyue.movieflex.utils

import android.app.Dialog
import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import top.hanyue.movieflex.R

object DialogFactory {

    fun getLoadingDialog(context: Context, msg: String? = context.getString(R.string.please_wait)): Dialog {
        val dialog = Dialog(context, R.style.LoadingDialog)
        val contentView = LayoutInflater.from(context).inflate(R.layout.view_dialog_loading, null)
        val aniImage = contentView.findViewById(R.id.ivLoading) as ImageView
        val msgView = contentView.findViewById(R.id.tvMessage) as TextView
        val ani = AnimationUtils.loadAnimation(context, R.anim.loading_ani)
        aniImage.startAnimation(ani)

        if (TextUtils.isEmpty(msg)) {
            msgView.visibility = View.GONE
        } else {
            msgView.visibility = View.VISIBLE
            msgView.text = msg
        }
        dialog.setCancelable(false)
        dialog.setContentView(contentView)
        dialog.show()
        return dialog
    }
}