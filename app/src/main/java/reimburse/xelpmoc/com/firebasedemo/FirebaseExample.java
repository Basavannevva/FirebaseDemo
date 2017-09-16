package reimburse.xelpmoc.com.firebasedemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Basavannevva on 13/09/17.
 */

public class FirebaseExample extends Activity implements  ListAdapter.firbaseUpadte{

    @Bind(R.id.editvalue)
    EditText editvalue;
    @Bind(R.id.imageDisplay)
    ImageView imageDisplay;
    int SELECT_IMAGE=1;
    private StorageReference riversRef;
    private StorageReference mStorageRef;
    private Uri filePath;
    private String fileName;
    private String path_file;
    ArrayList<ValueModel>list=new ArrayList<>();
    @Bind(R.id.listValues)
    RecyclerView listValues;
    @Bind(R.id.buttonUpdate)
    Button buttonUpdate;
    private String updateId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutsample);
        ButterKnife.bind(FirebaseExample.this);

    }

    /**
     * add Values to Firebase
     */
    @OnClick(R.id.buttonAdd)
    void addToFirebase()
    {
        final String idGroup = ("TEST" + System.currentTimeMillis()).hashCode() + "";
        ValueModel sample=new ValueModel();
        sample.values=editvalue.getText().toString();
        sample.name="Testing";

        FirebaseDatabase.getInstance().getReference().child("Test/" + idGroup).setValue(sample).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(FirebaseExample.this,"Added ",Toast.LENGTH_SHORT).show();
                editvalue.setText("");
            }
        });


    }

    /**
     * Retrive the values
     */
    @OnClick(R.id.buttonRetrive)
    void retriveFirebase()
    {
        list=new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("Test").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    HashMap mapMessage = (HashMap) childSnapshot.getValue();
                    String valueRetrive = (String) mapMessage.get("values");
                    String id = (String) childSnapshot.getKey();
                    Toast.makeText(FirebaseExample.this, valueRetrive, Toast.LENGTH_SHORT).show();

                    ValueModel model = new ValueModel();
                    model.values = valueRetrive;
                    model.id = id;
                    list.add(model);
                }


            ListAdapter adapter = new ListAdapter(FirebaseExample.this,list);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                listValues.setLayoutManager(mLayoutManager);
                listValues.setAdapter(adapter);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FirebaseDatabase.getInstance().getReference().child("Test").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    /**
     * Add image Storage
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                if (data != null)
                {
                    filePath = data.getData();
                    try
                    {
                        path_file = getPath(this, filePath);

                        mStorageRef = FirebaseStorage.getInstance().getReference();
                        riversRef = mStorageRef.child("AllFiles/" + path_file);
                        StorageTask<UploadTask.TaskSnapshot> uploadTask = riversRef.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        // Get a URL to the uploaded content
                                        Uri downloadUrl = taskSnapshot.getDownloadUrl();

                                        Glide.with(FirebaseExample.this).load(downloadUrl).placeholder(R.mipmap.ic_launcher)
                                                .fitCenter()
                                                .into(imageDisplay);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {

                                    }
                                });


                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }
            } else if (resultCode == Activity.RESULT_CANCELED)
            {
                Toast.makeText(FirebaseExample.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Open GAllary
     */
    @OnClick(R.id.buttonStorage)
    void addStorage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECT_IMAGE);
    }

    /**
     * get the Path of the image
     * @param context
     * @param uri
     * @return
     * @throws URISyntaxException
     */
    public String getPath(Context context, Uri uri) throws URISyntaxException {


        try {
            File myFile = new File(uri.toString());
            if (uri.toString().startsWith("content://")) {
                Cursor metaCursor = getApplication().getContentResolver().query(uri, null, null, null, null);
                if (metaCursor != null) {
                    try {
                        if (metaCursor.moveToFirst()) {
                            fileName = metaCursor.getString(metaCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        }
                    } finally {
                        metaCursor.close();
                    }
                }
            }
            else if (uri.toString().startsWith("file://")) {
                fileName = myFile.getName();
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }

        return fileName;

    }

    /**
     * Implemented method to handle Update and delete
     * @param id
     * @param action
     * @param text
     */
    @Override
    public void stateType(String id, String action,String text) {
        updateId=id;
        if(action.equalsIgnoreCase("Upadate"))
        {
            editvalue.setText(text);
            buttonUpdate.setVisibility(View.VISIBLE);

        }
        else if(action.equalsIgnoreCase("Delete"))
        {
            FirebaseDatabase.getInstance().getReference().child("Test/" + id).removeValue();
            Toast.makeText(FirebaseExample.this,"Deleted",Toast.LENGTH_SHORT).show();
            editvalue.setText("");
        }
    }

    /**
     * Update the The Values
     */
    @OnClick(R.id.buttonUpdate)
    void UpdateText()
    {
        DatabaseReference hopperRef = FirebaseDatabase.getInstance().getReference().child("Test/" + updateId);
        Map<String, Object> valueUpadte = new HashMap<String, Object>();
        valueUpadte.put("values", editvalue.getText().toString());
        hopperRef.updateChildren(valueUpadte);
        Toast.makeText(FirebaseExample.this,"Updated",Toast.LENGTH_SHORT).show();
        editvalue.setText("");
        buttonUpdate.setVisibility(View.GONE);
    }
}
