package com.example.fe.ui.todo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fe.data.Payment
import com.example.fe.databinding.ItemAdminPaymentBinding

class AdminPaymentAdapter(private val onItemClick: (Payment) -> Unit) :
    ListAdapter<Payment, AdminPaymentAdapter.PaymentViewHolder>(PaymentDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        val binding = ItemAdminPaymentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PaymentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        val payment = getItem(position)
        holder.bind(payment)
        holder.itemView.setOnClickListener { onItemClick(payment) }
    }

    class PaymentViewHolder(private val binding: ItemAdminPaymentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(payment: Payment) {
            binding.txtName.text = "Order: ${payment.order_id}"
            binding.tvStatus.text = payment.status
            // You can add more fields if needed, e.g., binding.tvAmount.text = payment.amount.toString()
        }
    }

    class PaymentDiffCallback : DiffUtil.ItemCallback<Payment>() {
        override fun areItemsTheSame(oldItem: Payment, newItem: Payment): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Payment, newItem: Payment): Boolean {
            return oldItem == newItem
        }
    }
}