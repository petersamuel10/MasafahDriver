package com.vavisa.masafahdriver.tap_profile.profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.vavisa.masafahdriver.R;
import com.vavisa.masafahdriver.activities.MainActivity;
import com.vavisa.masafahdriver.basic.BaseFragment;
import com.vavisa.masafahdriver.login.CountryModel;
import com.vavisa.masafahdriver.login.LoginActivity;
import com.vavisa.masafahdriver.register.UserModel;
import com.vavisa.masafahdriver.tap_profile.shipment_history.ShipmentHistoryFragment;
import com.vavisa.masafahdriver.tap_profile.termsAndConditions.TermsAndConditions;
import com.vavisa.masafahdriver.tap_profile.wallet.WalletActivity;
import com.vavisa.masafahdriver.util.Constants;
import com.vavisa.masafahdriver.util.GridSpaceItemDecoration;
import com.vavisa.masafahdriver.util.KeyboardUtil;
import com.vavisa.masafahdriver.util.Preferences;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends BaseFragment implements View.OnClickListener, ProfileViews {

    private View fragment;
    private CircleImageView user_image, user_image_update;
    private TextView user_name, user_email;
    private RecyclerView profileGridView;
    private List<Profile> profileItems;
    private ConstraintLayout profileLayout;
    private Button logout;
    private ProfilePresenter presenter;
    private UserModel user;
    private ArrayList<CountryModel> countriesList;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;
    private int country_id = 1;
    private DialogPlus my_details_dialog;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        if (fragment == null) {
            fragment = inflater.inflate(R.layout.fragment_profile, container, false);
            initViews();

            presenter = new ProfilePresenter();
            presenter.attachView(this);
            presenter.getProfileDetails();


        }

        setupProfileItems();

        profileGridView.setAdapter(new ProfileAdapter());

        return fragment;
    }

    private void changeLanguagePopMenu() {

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle(getString(R.string.select_language));
        String[] languages = new String[]{"English", "العربية"};

        int lan_pos = (Preferences.getInstance().getString("lan").equals("English")) ? 0 : 1;

        alert.setSingleChoiceItems(languages, lan_pos, (dialog, pos) -> {
            if (pos != lan_pos) {
                Preferences.getInstance().putString("lan", languages[pos]);
                Intent i = getContext().getPackageManager()
                        .getLaunchIntentForPackage(getContext().getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            } else
                dialog.dismiss();
        });

        alert.show();
    }

    private void updateDialog() {
        my_details_dialog =
                DialogPlus.newDialog(getActivity())
                        .setGravity(Gravity.BOTTOM)
                        .setContentBackgroundResource(R.drawable.rounded_corners_white_filled)
                        .setContentHolder(new ViewHolder(R.layout.profile_view))
                        .create();

        ImageView close_btn = (ImageView) my_details_dialog.findViewById(R.id.imageView);
        Button update_btn = (Button) my_details_dialog.findViewById(R.id.update_btn);
        user_image_update = (CircleImageView) my_details_dialog.findViewById(R.id.user_image_update);
        FloatingActionButton add_image_btn = (FloatingActionButton) my_details_dialog.findViewById(R.id.add_image_btn);
        EditText fullName_ed = (EditText) my_details_dialog.findViewById(R.id.full_name);
        EditText email_ed = (EditText) my_details_dialog.findViewById(R.id.email);
        EditText phone_ed = (EditText) my_details_dialog.findViewById(R.id.phone);
        Button country_code_btn = (Button) my_details_dialog.findViewById(R.id.country_code);
        try {

            fullName_ed.setText(user.getName());
            email_ed.setText(user.getEmail());
            phone_ed.setText(user.getMobile());
            country_code_btn.setText(user.getCountryModel().getCountry_code());
            country_id = user.getCountryModel().getId();

            Glide.with(this)
                    .load(user.getImage())
                    .centerCrop()
                    .placeholder(R.drawable.ic_account_circle)
                    .error(R.drawable.ic_account_circle)
                    .into(user_image_update);
        } catch (Exception e) {
        }
        update_btn.setOnClickListener(v -> {
            if (validate(fullName_ed, email_ed, phone_ed)) {
                UserModel user = new UserModel(fullName_ed.getText().toString(),
                        email_ed.getText().toString(),
                        phone_ed.getText().toString(),
                        presenter.getImageBase64FromView(user_image_update),
                        country_id);
                presenter.updateProfileDetails(user);
                KeyboardUtil.hideKeyboard(getContext(), update_btn);
            }
        });
        country_code_btn.setOnClickListener(v -> countryCodeAlert(country_code_btn));
        add_image_btn.setOnClickListener(v -> addNewImage());
        close_btn.setOnClickListener(v -> my_details_dialog.dismiss());

        my_details_dialog.show();
    }

    private boolean validate(EditText fullName_ed, EditText email_ed, EditText phone_ed) {

        if (TextUtils.isEmpty(fullName_ed.getText())) {
            showMessage(getString(R.string.please_enter_name));
            return false;
        }
        if (TextUtils.isEmpty(email_ed.getText())) {
            showMessage(getString(R.string.please_enter_valid_phone_number));
            return false;
        }
        if (TextUtils.isEmpty(phone_ed.getText())) {
            showMessage(getString(R.string.please_enter_valid_email));
            return false;
        }

        return true;
    }

    private void countryCodeAlert(Button country_code_btn) {

        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(getContext());
        alert.setTitle(getString(R.string.select_country));
        String[] countries_name = new String[countriesList.size()];
        for (int i = 0; i < countriesList.size(); i++) {
            countries_name[i] = countriesList.get(i).getName();
        }

        alert.setSingleChoiceItems(countries_name, 0, (dialog, position) -> {
            dialog.dismiss();
            country_code_btn.setText(countriesList.get(position).getCountry_code());
            country_id = countriesList.get(position).getId();

        });
        alert.create().show();
    }

    private void addNewImage() {

        String[] items = {getString(R.string.camera), getString(R.string.gallery)};

        AlertDialog.Builder select_pic_alert = new AlertDialog.Builder(getContext());
        select_pic_alert.setTitle(getString(R.string.select_picture_from));
        select_pic_alert.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int index) {

                if (index == 0) {
                    Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (camera_intent.resolveActivity(getContext().getPackageManager()) != null)
                        startActivityForResult(camera_intent, PICK_IMAGE_CAMERA);
                } else
                    startActivityForResult(new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI), PICK_IMAGE_GALLERY);
            }
        });

        select_pic_alert.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmap = null;

        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                case PICK_IMAGE_CAMERA:
                    bitmap = (Bitmap) data.getExtras().get("data");
                    break;

                case PICK_IMAGE_GALLERY:
                    Uri selectedImage = data.getData();

                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            Glide.with(getContext()).asBitmap().load(bitmap).into(user_image_update);
        }
    }

    private void initViews() {

        Toolbar toolbar = fragment.findViewById(R.id.profile_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        getActivity().setTitle("");

        profileLayout = fragment.findViewById(R.id.profile_layout);
        profileGridView = fragment.findViewById(R.id.profile_items);
        logout = fragment.findViewById(R.id.logout_button);
        user_image = fragment.findViewById(R.id.profile_image);
        user_name = fragment.findViewById(R.id.user_name);
        user_email = fragment.findViewById(R.id.user_email);
        //user_phone = fragment.findViewById(R.id.user_phone);

        logout.setOnClickListener(this);
        profileGridView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        profileGridView.addItemDecoration(new GridSpaceItemDecoration(25));

        profileLayout.post(() -> {
            int height = profileLayout.getHeight();
            RelativeLayout.LayoutParams layoutParams =
                    (RelativeLayout.LayoutParams) profileLayout.getLayoutParams();
            layoutParams.topMargin = -(height / 3);
            profileLayout.setLayoutParams(layoutParams);
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout_button:
                HashMap<String, String> player_id = new HashMap<>();
                player_id.put("player_id", Constants.oneSignalToken);
                presenter.logout(player_id);
                break;
        }
    }

    @Override
    public void displayProfileDetails(UserModel user) {
        if (my_details_dialog != null && my_details_dialog.isShowing())
            my_details_dialog.dismiss();

        this.user = user;
        user_name.setText(user.getName());
        user_email.setText(user.getEmail());

        Glide.with(this)
                .load(user.getImage())
                .centerCrop()
                .placeholder(R.drawable.ic_account_circle)
                .error(R.drawable.ic_account_circle)
                .into(user_image);

    }

    @Override
    public void logout() {

        getActivity()
                .getSupportFragmentManager()
                .popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
    }

    @Override
    public void getCountriesList(ArrayList<CountryModel> countryList) {
        this.countriesList = countryList;
    }

    private class ProfileViewHolder extends RecyclerView.ViewHolder {

        private ImageView itemIcon;
        private TextView itemName;

        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            itemIcon = itemView.findViewById(R.id.item_icon);
            itemName = itemView.findViewById(R.id.item_name);
        }

        public void bindData(Profile profile) {
            itemIcon.setImageResource(profile.getImage());
            itemName.setText(profile.getName());
        }
    }

    private class ProfileAdapter extends RecyclerView.Adapter<ProfileViewHolder> {

        @NonNull
        @Override
        public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v =
                    LayoutInflater.from(viewGroup.getContext())
                            .inflate(R.layout.profile_list_item, viewGroup, false);

            return new ProfileViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ProfileViewHolder profileViewHolder, int i) {
            profileViewHolder.bindData(profileItems.get(i));

            final int position = profileViewHolder.getAdapterPosition();

            profileViewHolder.itemView.setOnClickListener(
                    v -> {
                        Fragment fragment = null;
                        switch (position) {
                            case 0:
                                presenter.getCountries();
                                updateDialog();
                                break;
                            case 1:
                                changeLanguagePopMenu();
                                break;
                            case 2:
                                fragment = new ShipmentHistoryFragment();
                                ((MainActivity) getActivity()).pushFragments(Constants.TAB_PROFILE, fragment, true);
                                break;
                            case 3:
                                fragment = new TermsAndConditions();
                                ((MainActivity) getActivity()).pushFragments(Constants.TAB_PROFILE, fragment, true);
                                break;

                            case 4:
                                startActivity(new Intent(getActivity(), WalletActivity.class));
                                break;
                        }
                    });
        }

        @Override
        public int getItemCount() {
            return profileItems.size();
        }
    }

    private void setupProfileItems() {
        profileItems = new ArrayList<>();
        Profile profile = new Profile();

        profile.setImage(R.drawable.ic_account_circle_primary_24dp);
        profile.setName(getString(R.string.my_details));

        profileItems.add(profile);

        profile = new Profile();
        profile.setImage(R.drawable.ic_language_primary_24dp);
        profile.setName(getString(R.string.language));

        profileItems.add(profile);

        profile = new Profile();
        profile.setImage(R.drawable.ic_box);
        profile.setName(getString(R.string.shipment_history));

        profileItems.add(profile);

        profile = new Profile();
        profile.setImage(R.drawable.ic_document);
        profile.setName(getString(R.string.termsandconditions));

        profileItems.add(profile);

        profile = new Profile();
        profile.setImage(R.drawable.ic_document);
        profile.setName(getString(R.string.wallet));

        profileItems.add(profile);
    }

    private class Profile {
        int image;
        String name;

        public int getImage() {
            return image;
        }

        public void setImage(int image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
