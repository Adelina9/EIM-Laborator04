package ro.pub.cs.systems.eim.lab04.contactsmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactsManagerActivity extends AppCompatActivity {
    Button showOrHideDetailsButton;
    Button saveButton;
    Button cancelButton;
    EditText nameEditText;
    EditText phoneNumberEditText;
    EditText emailEditText;
    EditText addressEditText;
    EditText jobTitleEditText;
    EditText companyEditText;
    EditText websiteEditText;
    EditText imEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_manager);
        showOrHideDetailsButton = findViewById(R.id.showOrHideDetailsButton);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);
        nameEditText = findViewById(R.id.nameEditText);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        emailEditText = findViewById(R.id.emailEditText);
        addressEditText = findViewById(R.id.addressEditText);
        jobTitleEditText = findViewById(R.id.jobTitleEditText);
        companyEditText = findViewById(R.id.companyEditText);
        websiteEditText = findViewById(R.id.websiteEditText);
        imEditText = findViewById(R.id.imEditText);

        showOrHideDetailsButton.setOnClickListener(new ButtonClick());
        saveButton.setOnClickListener(new ButtonClick());
        cancelButton.setOnClickListener(new ButtonClick());

        Intent intent = getIntent();
        if (intent != null) {
            String phone = intent.getStringExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY");
            if (phone != null) {
                phoneNumberEditText.setText(phone);
            } else {
                Toast.makeText(this, getResources().getString(R.string.phone_error), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
//            case Constants.CONTACTS_MANAGER_REQUEST_CODE:
            case 3:
                setResult(resultCode, new Intent());
                finish();
                break;
        }
    }

    class ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.showOrHideDetailsButton) {
                LinearLayout container2 = findViewById(R.id.container2);
                if (container2.getVisibility() == View.GONE) {
                    container2.setVisibility(View.VISIBLE);
                    showOrHideDetailsButton.setText(R.string.hide_additional_fields);
                } else {
                    container2.setVisibility(View.GONE);
                    showOrHideDetailsButton.setText(R.string.show_additional_fields);
                }
            } else if (view.getId() == R.id.saveButton) {
                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                String name = nameEditText.getText().toString();
                String phone = phoneNumberEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String address = addressEditText.getText().toString();
                String jobTitle = jobTitleEditText.getText().toString();
                String company = companyEditText.getText().toString();
                String website = websiteEditText.getText().toString();
                String im = imEditText.getText().toString();
                if (name != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
                }
                if (phone != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
                }
                if (email != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
                }
                if (address != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
                }
                if (jobTitle != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle);
                }
                if (company != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
                }
                ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
                if (website != null) {
                    ContentValues websiteRow = new ContentValues();
                    websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                    websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
                    contactData.add(websiteRow);
                }
                if (im != null) {
                    ContentValues imRow = new ContentValues();
                    imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                    imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
                    contactData.add(imRow);
                }
                intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
                // startActivity(intent);
                startActivityForResult(intent, 3);
            } else if (view.getId() == R.id.cancelButton) {
                setResult(Activity.RESULT_CANCELED, new Intent());
                finish();
            }
        }
    }
}