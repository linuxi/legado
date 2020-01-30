package io.legado.app.ui.importbook

import android.content.Context
import androidx.documentfile.provider.DocumentFile
import io.legado.app.R
import io.legado.app.base.adapter.ItemViewHolder
import io.legado.app.base.adapter.SimpleRecyclerAdapter
import io.legado.app.constant.AppConst
import io.legado.app.utils.StringUtils
import io.legado.app.utils.gone
import io.legado.app.utils.invisible
import io.legado.app.utils.visible
import kotlinx.android.synthetic.main.item_import_book.view.*
import org.jetbrains.anko.sdk27.listeners.onClick
import java.util.*


class ImportBookAdapter(context: Context, val callBack: CallBack) :
    SimpleRecyclerAdapter<DocumentFile>(context, R.layout.item_import_book) {


    override fun convert(holder: ItemViewHolder, item: DocumentFile, payloads: MutableList<Any>) {
        holder.itemView.apply {
            if (item.isDirectory) {
                iv_icon.visible()
                cb_select.invisible()
                ll_brief.gone()
                tv_sub_count.visible()
                tv_sub_count.text =
                    context.getString(R.string.nb_file_sub_count, item.listFiles().size)
            } else {
                iv_icon.invisible()
                cb_select.visible()
                ll_brief.visible()
                tv_sub_count.gone()
                tv_tag.text = item.name?.substringAfterLast(".")
                tv_size.text = StringUtils.toSize(item.length())
                tv_date.text = AppConst.DATE_FORMAT.format(Date(item.lastModified()))
            }
            tv_name.text = item.name
            onClick {
                item.name?.let { name ->
                    if (item.isDirectory) {
                        callBack.findFolder(name)
                    } else {
                        cb_select.isChecked = !cb_select.isChecked
                    }
                }
            }
        }
    }

    interface CallBack {
        fun findFolder(dirName: String)
    }

}