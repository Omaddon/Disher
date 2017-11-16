package com.ammyt.disher.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.ammyt.disher.R
import com.ammyt.disher.model.Table

class TableBillActivity : AppCompatActivity() {

    companion object {
        val NEW_TABLE_DELETED = "NEW_TABLE_DELETED"
        private val TABLE_FOR_BILL = "TABLE_FOR_BILL"

        fun newIntent(context: Context, table: Table): Intent {
            val intent = Intent(context, TableBillActivity::class.java)
            intent.putExtra(TABLE_FOR_BILL, table)

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_bill)

        val table = intent.getSerializableExtra(TABLE_FOR_BILL) as? Table

        val tableName = findViewById<TextView>(R.id.table_name)
        val dishes = findViewById<TextView>(R.id.dish_of_table_count)
        val bill = findViewById<TextView>(R.id.table_bill)

        tableName.text = table?.name
        dishes.text = getString(R.string.dishes_amount, table?.count)

        var dishesBill = 0.00f

        if (table != null) {
            if (table.count > 0) {
                for (dishIndex in 0 until table.count) {
                    val dishPrice = table.dishes?.get(dishIndex)?.price
                    dishPrice?.let {
                        dishesBill += dishPrice
                    }
                }
            }
        }

        bill.text = getString(R.string.bill_format, dishesBill)

        findViewById<Button>(R.id.pay_bill_button).setOnClickListener { payBill(table) }
        findViewById<Button>(R.id.back_bill_button).setOnClickListener { backBill() }
    }

    private fun backBill() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    private fun payBill(table: Table?) {
        AlertDialog.Builder(this)
                .setTitle("Caution!!")
                .setMessage("Dishes for this table will be delete!! Are you sure?")
                .setPositiveButton("YES", { dialog, which ->
                    dialog.dismiss()

                    table?.dishes?.clear()

                    val returnIntent = Intent()
                    returnIntent.putExtra(NEW_TABLE_DELETED, table)

                    setResult(Activity.RESULT_OK, returnIntent)
                    finish()
                })
                .setNegativeButton("CANCEL", { dialog, which ->
                    dialog.dismiss()
                })
                .show()
    }
}
