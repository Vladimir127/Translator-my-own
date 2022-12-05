package com.example.translator.presentation.word

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.translator.R
import com.example.translator.entities.Meanings

class WordAdapter(
    private var onListItemClickListener: OnListItemClickListener,
    private var data: List<Meanings>
) :
    RecyclerView.Adapter<WordAdapter.RecyclerItemViewHolder>() {

    fun setData(data: List<Meanings>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_word_recyclerview_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: Meanings) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.findViewById<TextView>(R.id.meaning_textview_recycler_item).text = (layoutPosition + 1).toString() + ". " + data.translation?.translation
                itemView.findViewById<TextView>(R.id.transcription_textview_recycler_item).text =
                    data.transcription
                itemView.findViewById<TextView>(R.id.part_of_speech_textview_recycler_item).text = data.partOfSpeechCode
                itemView.setOnClickListener { openInNewWindow(data) }
            }
        }
    }

    private fun openInNewWindow(listItemData: Meanings) {
        onListItemClickListener.onItemClick(listItemData)
    }

    interface OnListItemClickListener {
        fun onItemClick(data: Meanings)
    }
}
