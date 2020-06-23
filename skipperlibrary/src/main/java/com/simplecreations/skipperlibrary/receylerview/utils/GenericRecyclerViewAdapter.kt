/*
 * Copyright 2020 ARUNWORLDLOGIN@GMAIL.COM. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.simplecreations.skipperlibrary.receylerview.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.simplecreations.skipperlibrary.receylerview.utils.GenericRecyclerViewAdapter.ViewBinder

/**
 * Generic recycler view adapter
 * @param ModelType The data type of items stored in [viewHolderData]
 * @constructor Creates a [RecyclerView.Adapter] instance
 * @param context [Context]
 * @param viewHolderLayout Id of view holder layout file.
 * @param viewHolderBinder [ViewBinder] object implementation for binding layout with data.
 * @param viewHolderData [ArrayList] of [ModelType] used to fill each recycler view items.
 * @param emptyStateViewHolderLayout Optional : Id of empty state view holder layout file.
 * @param emptyStateViewHolderBinder Optional : [ViewBinder] object implementation for binding empty state layout with data.
 * @param emptyStateViewHolderData Optional : [Any] data used to bind empty data with empty state layout.
 * @author Arun PM
 * **/

class GenericRecyclerViewAdapter<ModelType : Any>(
    private val context: Context,
    private val viewHolderLayout: Int,
    private val viewHolderBinder: ViewBinder,
    private val viewHolderData: ArrayList<ModelType>,
    private val emptyStateViewHolderLayout: Int? = null,
    private val emptyStateViewHolderBinder: ViewBinder? = null,
    private val emptyStateViewHolderData: Any? = null,
    var clickListener: OnClickListener<ModelType>? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var emptyState = false


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (!emptyState) ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                viewHolderLayout,
                parent,
                false
            ), viewHolderBinder
        )
        else EmptyStateViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                emptyStateViewHolderLayout!!,
                parent,
                false
            ), emptyStateViewHolderBinder!!
        )
    }

    override fun getItemCount(): Int {
        return if (emptyStateViewHolderBinder != null &&
            emptyStateViewHolderData != null && viewHolderData.size == 0
        ) {
            emptyState = true
            1
        } else {
            emptyState = false
            viewHolderData.size
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder)
            holder.apply {
                binder.bind(viewHolderData[position], binding)
                clickListener?.let { listener ->
                    binding.root.setOnClickListener {
                        listener.onClick(position, viewHolderData[position])
                    }
                }
            }
        else if (holder is EmptyStateViewHolder)
            emptyStateViewHolderData?.let {
                holder.binder.bind(it, holder.binding)
            }
    }

    class ViewHolder(val binding: ViewDataBinding, val binder: ViewBinder) :
        RecyclerView.ViewHolder(binding.root)

    class EmptyStateViewHolder(val binding: ViewDataBinding, val binder: ViewBinder) :
        RecyclerView.ViewHolder(binding.root)

    interface ViewBinder {
        fun bind(data: Any, binding: ViewDataBinding)
    }

    interface OnClickListener<ModelType : Any> {
        fun onClick(position: Int, data: ModelType)
    }
}