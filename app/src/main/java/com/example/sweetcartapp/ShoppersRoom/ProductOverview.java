
package com.example.sweetcartapp.ShoppersRoom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.sweetcartapp.R;
import com.example.sweetcartapp.ShoppersRoom.Commons.BroadCasterInfo;
import com.example.sweetcartapp.ShoppersRoom.Commons.CartItemsAndImagesList;
import com.example.sweetcartapp.ShoppersRoom.FragmentsBaseActivity.Home;
import com.example.sweetcartapp.ShoppersRoom.HelperMethods.Users;

import java.util.ArrayList;
import java.util.List;

public class ProductOverview extends AppCompatActivity implements GestureDetector.OnGestureListener {
    private GestureDetector gestureDetector;
    private double SWIPE_THRESHOLD = 100;
    private double SWIPE_VELOCITY_THRESHOLD = 100;

    String productTitle;
    Integer imagetobeLoaded;


    TextView qtyamount, titleProduct;
    ImageView productPhoto;
    Button addToCart;

    LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_overview);

        initializeViews();
        setImageAndTitleUsingCallingActivity();
        setSpinner();

        performButtonClickOperation();
        this.gestureDetector = new GestureDetector(this, this);
        setSwipeDownGesturetoFinishActivity();

    }

    private void setSwipeDownGesturetoFinishActivity() {
        productPhoto.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }

        });
    }

    private void performButtonClickOperation() {
        performAddtoCartOperation();
    }

    private void performAddtoCartOperation() {
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = ++Home.mCartItemCount;
                Log.d("BadgeCOUNT", "onClick: addValueInCart: " + value);
                /*BroadCast Listener: Requirement FOR SENDER
                 * Create field in activity that wants to send BroadCast
                 *  LocalBroadcastManager localBroadcastManager;
                 * localBroadcastManager = LocalBroadcastManager.getInstance(this);
                 *  Create a function to send BroadCast() defined using
                 * Intents having common variable in receiver and sender;
                 * For e.g.,
                 *  private void startMyItemAddedBroadCast() {
                                 Intent intent = new Intent(BroadCasterInfo.CART_BADGE);
                                localBroadcastManager.sendBroadcast(intent);
                                }
                 *
                 * Requirement: FOR RECEIVER
                 *      LocalBroadcastManager localBMFragments;
                 *
                 * Set listener action for text change or any other purpose
                 *   private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
                        @Override
                        public void onReceive(Context context, Intent intent) {
                             setupBadge();
                        }
                    };
                 *
                 * In onCreate(){
                  localBMFragments = LocalBroadcastManager.getInstance(getActivity());
        localBMFragments.registerReceiver(broadcastReceiver, new IntentFilter(BroadCasterInfo.CART_BADGE));
                 }
                 *
                 * Override an onDestroyMethod() to unregister Receiver
                 *  @Override
                        public void onDestroy() {
                            super.onDestroy();
                            localBMFragments.unregisterReceiver(broadcastReceiver);

                        }
                 *
                 * *
                 * */
                startMyItemAddedBroadCast();
                CartItemsAndImagesList doneAdd = new CartItemsAndImagesList();
                if (!CartItemsAndImagesList.titleID.contains(productTitle)) {

                }
                doneAdd.addtitleId(productTitle);
                doneAdd.addImageId(imagetobeLoaded);
                if (!CartItemsAndImagesList.imageId.contains(imagetobeLoaded)) {

                }
                finish();
            }
        });
    }


    private void setImageAndTitleUsingCallingActivity() {
        productTitle = getIntent().getStringExtra("Title");
        imagetobeLoaded = getIntent().getIntExtra("ImageID", Integer.MAX_VALUE);
        loadImagesandSetTitle(productTitle, imagetobeLoaded);
    }

    private void initializeViews() {
        qtyamount = findViewById(R.id.qtyTextChange);
        titleProduct = findViewById(R.id.tile_in_overview);
        productPhoto = findViewById(R.id.detailed_activity_image);
        addToCart = findViewById(R.id.addToCartButton);

        localBroadcastManager = LocalBroadcastManager.getInstance(this);
    }

    private void loadImagesandSetTitle(String productTitle, Integer imagetobeLoaded) {
        titleProduct.setText(productTitle);
        productPhoto.setImageResource(imagetobeLoaded);
    }

    private void setSpinner() {
        Users ob1 = new Users(1, "Select Quantity");
        Users ob2 = new Users(2, "100 g");
        Users ob3 = new Users(3, "150 g");
        Users ob4 = new Users(4, "250 g");
        Users ob5 = new Users(5, "500 g");
        Users ob6 = new Users(6, "1 kg");
        Users ob7 = new Users(7, "2 kg");
        Users ob8 = new Users(8, "5 kg");

        List<Users> users = new ArrayList<>();
        users.add(ob1);
        users.add(ob2);
        users.add(ob3);
        users.add(ob4);
        users.add(ob5);
        users.add(ob6);
        users.add(ob7);
        users.add(ob8);

        SetupSpinner(users);


    }

    private void startMyItemAddedBroadCast() {
        Intent intent = new Intent(BroadCasterInfo.CART_BADGE);
        localBroadcastManager.sendBroadcast(intent);
    }

    private void SetupSpinner(List<Users> users) {
        Spinner spinner = findViewById(R.id.qtySpinner);
        ArrayAdapter userAdapter = new ArrayAdapter(this, R.layout.spinner, users);
        userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(userAdapter);

        //Set item selected listener on spinner dropdown
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s = "" + userAdapter.getItem(position);
                if (!s.equals("Select Quantity")) {
                    qtyamount.setText(s);
                    //TODO : Run tax calculation class here to maintain invoice for
                    // each value selected by the user
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }


    @Override
    public boolean onFling(MotionEvent downEvent, MotionEvent moveEvent, float velocityX, float velocityY) {
        boolean result = false;
        float diffY = moveEvent.getY() - downEvent.getY();
        float diffX = moveEvent.getX() - downEvent.getX();
        // which was greater?  movement across Y or X?
        if (Math.abs(diffX) > Math.abs(diffY)) {
            // right or left swipe
            if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffX > 0) {
                    onSwipeRight();
                } else {
                    onSwipeLeft();
                }
                result = true;
            }
        } else {
            // up or down swipe
            if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffY > 0) {
                    onSwipeBottom();
                } else {
                    onSwipeTop();
                }
                result = true;
            }
        }

        return result;
    }

    private void onSwipeLeft() {
    }

    private void onSwipeBottom() {
        finish();

    }

    private void onSwipeTop() {

    }

    private void onSwipeRight() {
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}