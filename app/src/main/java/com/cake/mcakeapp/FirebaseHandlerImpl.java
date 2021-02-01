package com.cake.mcakeapp;

import com.cake.mcakeapp.data.ProductData;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseHandlerImpl implements FirebaseHandler {


    private FirebaseFirestore firebaseFirestore;

    public FirebaseHandlerImpl() {
       firebaseFirestore = FirebaseFirestore.getInstance();
    }


    @Override
    public void onCatchAllProductList(OnCatchDataFromFirebase<ProductData> listener) {
        
    }
}
