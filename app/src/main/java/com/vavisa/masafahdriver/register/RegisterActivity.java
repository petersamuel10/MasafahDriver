package com.vavisa.masafahdriver.register;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.vavisa.masafahdriver.R;
import com.vavisa.masafahdriver.basic.BaseActivity;
import com.vavisa.masafahdriver.login.CountryModel;
import com.vavisa.masafahdriver.login.LoginActivity;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends BaseActivity implements RegisterViews {

    private ImageView back_arrow;
    private CircleImageView user_image;
    private FloatingActionButton add_img_btn;
    private TextInputEditText name_ed, mobile_ed,
            email_ed, password_ed,
            confirm_password_ed;
    private Button country_code_btn, continueButton;

    private String name_str, mobile_str,
            email_str, password_str,
            confirm_password_str, image_str;
    private Bitmap bitmap = null;

    private ArrayList<CountryModel> countriesList;
    private Integer select_country_pos = 0, country_id = 114;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;
    private RegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();

        presenter = new RegisterPresenter();
        presenter.attachView(this);
        presenter.getCountries();

        back_arrow.setOnClickListener(v -> onBackPressed());
        add_img_btn.setOnClickListener(v1 -> addNewImage());
        country_code_btn.setOnClickListener(v -> {

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(getString(R.string.select_country));
            String[] countries_name = new String[countriesList.size()];
            for (int i = 0; i < countriesList.size(); i++) {
                countries_name[i] = countriesList.get(i).getName();
            }

            alert.setSingleChoiceItems(countries_name, select_country_pos, (dialog, position) -> {
                dialog.dismiss();
                select_country_pos = position;
                country_code_btn.setText(countriesList.get(position).getCountry_code());
                country_id = countriesList.get(position).getId();

            });
            alert.create().show();
        });

        continueButton.setOnClickListener(v -> {
            if (validate()) {
                UserModel user = new UserModel(name_str, email_str, mobile_str, password_str, confirm_password_str, image_str, country_id);
                presenter.register(user);
            }
        });

    }

    private void addNewImage() {

        String[] items = {getString(R.string.camera), getString(R.string.gallery)};

        android.support.v7.app.AlertDialog.Builder select_pic_alert = new android.support.v7.app.AlertDialog.Builder(this);
        select_pic_alert.setTitle(getString(R.string.select_picture_from));
        select_pic_alert.setItems(items, (dialog, index) -> {

            if (index == 0) {
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (camera_intent.resolveActivity(getPackageManager()) != null)
                    startActivityForResult(camera_intent, PICK_IMAGE_CAMERA);
            } else
                startActivityForResult(new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI), PICK_IMAGE_GALLERY);
        });

        select_pic_alert.show();
    }

    private boolean validate() {

        if (bitmap == null) {
            showMessage(getString(R.string.please_select_picture));
            return false;
        } else
            image_str = presenter.getImageBase64FromView(user_image);

        if (TextUtils.isEmpty(name_ed.getText())) {
            showMessage(getString(R.string.please_enter_name));
            return false;
        } else
            name_str = name_ed.getText().toString();

        if (TextUtils.isEmpty(mobile_ed.getText())) {
            showMessage(getString(R.string.please_enter_valid_phone_number));
            return false;
        } else
            mobile_str = mobile_ed.getText().toString();

        if (TextUtils.isEmpty(email_ed.getText())) {
            showMessage(getString(R.string.please_enter_valid_email));
            return false;
        } else
            email_str = email_ed.getText().toString();

        if (TextUtils.isEmpty(password_ed.getText())) {
            showMessage(getString(R.string.please_enter_password));
            return false;
        } else
            password_str = password_ed.getText().toString();

        if (TextUtils.isEmpty(confirm_password_ed.getText())) {
            showMessage(getString(R.string.please_enter_confirm_password));
            return false;
        } else
            confirm_password_str = confirm_password_ed.getText().toString();

        if (!password_str.equals(confirm_password_str)) {
            showMessage(getString(R.string.confirm_password_not_match_password));
            return false;
        }

        return true;
    }

    private void initViews() {

        back_arrow = findViewById(R.id.back_arrow);
        user_image = findViewById(R.id.user_logo);
        add_img_btn = findViewById(R.id.ic_add_profile_image);
        name_ed = findViewById(R.id.name_ed);
        mobile_ed = findViewById(R.id.mobile_ed);
        email_ed = findViewById(R.id.email_ed);
        password_ed = findViewById(R.id.password_ed);
        confirm_password_ed = findViewById(R.id.confirm_password_ed);
        country_code_btn = findViewById(R.id.country_code);
        continueButton = findViewById(R.id.continue_buton);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                case PICK_IMAGE_CAMERA:
                    bitmap = (Bitmap) data.getExtras().get("data");
                    break;

                case PICK_IMAGE_GALLERY:
                    Uri selectedImage = data.getData();

                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }

            Glide.with(this).asBitmap().load(bitmap).into(user_image);
        }
    }

    @Override
    public void handleRegister(RegisterResponse registerResponse) {
        start(LoginActivity.class);
    }

    @Override
    public void displayCountries(ArrayList<CountryModel> countryList) {
        this.countriesList = countryList;
    }
}
