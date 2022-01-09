package com.example.justjava;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {



    int quantity = 1;


    //This method is called when the plus button is clicked.
    public void increment(View view) {
        if (quantity == 100) {
            Toast tooManyCups = Toast.makeText(this, "You can only order upto 100 cups of coffee in an order",Toast.LENGTH_SHORT);
            tooManyCups.show();
            return;
        }

        quantity += 1;

        displayQuantity(quantity);


    }

    /**
     * Method to send an email about the order summary

     */
    public void composeEmail(String message) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
//        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.putExtra(Intent.EXTRA_SUBJECT,"Your Just Java coffee order is on the way!");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }




    //This method is called when the minus button is clicked.
    public void decrement(View view) {
        if (quantity != 1) {
            quantity -= 1;

            displayQuantity(quantity);
        } else {

            Toast tooManyCups = Toast.makeText(this, "You need to order atleast 1 cup of coffee in the order.", Toast.LENGTH_SHORT);
            tooManyCups.show();
            return;


        }

    }

    //This method is called when the order button is clicked. It displays the no. of cups and the price for the cups.
    public void submitOrder(View view) {
        boolean hasWhippedCream = ((CheckBox) findViewById(R.id.whipped_cream)).isChecked();
        boolean hasChocolate = ((CheckBox) findViewById(R.id.chocolate_checkbox)).isChecked();
        int pricePerCup = 5;
        if (hasWhippedCream) {
            pricePerCup += 1;
        }
        if (hasChocolate) {
            pricePerCup += 2;
        }

        int price = calculatePrice(pricePerCup);
        String summaryOfOrder = createOrderSummary(price, hasWhippedCream, hasChocolate);

        displayMessage(summaryOfOrder);
        composeEmail(summaryOfOrder);


    }

//This method calculated the price of coffees.
    private int calculatePrice(int costOfOne) {

        return quantity * costOfOne;

    }

    /**
     * This method returns the order summary
     */
    private String createOrderSummary(int price, boolean whippedCream, boolean chocolate) {
        EditText name_input = (EditText) findViewById(R.id.name_input);
        Editable name = name_input.getText();
        String summary = "Name: " + name + "\nWhipped cream? " + whippedCream + "\nChocolate? " + chocolate + "\nQuantity: " + quantity + "\nTotal: $" + price + "\nThank you!";

        return summary;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);

        quantityTextView.setText("" + number);

    }


    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }



    /**
    Starts the app on activity_main.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}