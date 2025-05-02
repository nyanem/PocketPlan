package com.example.pocketplan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TransactionAdapter(private val transactions: List<Transaction>) :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction, parent, false)
        return TransactionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.bind(transaction)
    }

    override fun getItemCount() = transactions.size

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val amountText: TextView = itemView.findViewById(R.id.textViewAmount)
        private val categoryText: TextView = itemView.findViewById(R.id.textViewCategory)
        private val dateText: TextView = itemView.findViewById(R.id.textViewDate)

        fun bind(transaction: Transaction) {
            // Format amount with currency symbol and two decimal places
            val formattedAmount = "R${String.format("%.2f", transaction.amount)}"
            amountText.text = formattedAmount

            // Set category and date
            categoryText.text = transaction.category
            dateText.text = transaction.date
        }
    }
}