package ercanduman.android.audiorecorder.ui.main.recording.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ercanduman.android.audiorecorder.data.model.Record
import ercanduman.android.audiorecorder.databinding.ListItemRecordingBinding

class RecordingsAdapter(
    private val clickListener: OnRecordClickedListener
) : ListAdapter<Record, RecordingsAdapter.RecordViewHolder>(recordComparator) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecordViewHolder {
        val binding =
            ListItemRecordingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.bindRecord(getRecordAt(position))
    }

    fun getRecordAt(position: Int): Record {
        return getItem(position)
    }

    inner class RecordViewHolder(
        private val binding: ListItemRecordingBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                /* If item clicked during deletion or new insertion processes, then it is possible
                that clicked item's position might be invalid which is animating during process. */
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val currentRecord = getRecordAt(adapterPosition)
                    clickListener.onRecordClicked(currentRecord)
                }
            }
        }

        fun bindRecord(record: Record) {
            with(binding) {
                recordingName.text = record.name
                recordingDuration.text = record.duration.toString()
            }
        }
    }

    companion object {
        private val recordComparator = object : DiffUtil.ItemCallback<Record>() {
            override fun areItemsTheSame(oldItem: Record, newItem: Record): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Record, newItem: Record): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface OnRecordClickedListener {
        fun onRecordClicked(record: Record)
    }
}