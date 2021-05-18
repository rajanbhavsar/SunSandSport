package com.sunsandsports.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sunsandsports.R
import com.sunsandsports.databinding.ItemUserLayoutBinding
import com.sunsandsports.listener.clickListener
import com.sunsandsports.ui.User


/**
 * This is the User List Adapter where we are binding the List with RecycleView.
 *
 */
class UserListAdapter(
    private val mContext: Context,
    private val mListData: ArrayList<User>,
    private val clickListener: clickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_user_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val eachListData = mListData[position]
        (holder as ViewHolder).mBinding?.user = eachListData
        (holder as ViewHolder).mBinding?.position = "" + position


        (holder as ViewHolder).itemView?.setOnClickListener {
            clickListener.onUserItemClick(eachListData)
        }
    }

    override fun getItemCount(): Int {
        return mListData.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mBinding: ItemUserLayoutBinding? = DataBindingUtil.bind(itemView)
    }
}