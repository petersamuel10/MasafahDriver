package com.vavisa.masafahdriver.tap_profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.vavisa.masafahdriver.R;
import com.vavisa.masafahdriver.basic.BaseFragment;
import com.vavisa.masafahdriver.tap_profile.wallet.WalletActivity;
import com.vavisa.masafahdriver.tap_profile.shipment_history.ShipmentHistoryFragment;
import com.vavisa.masafahdriver.login.LoginActivity;
import com.vavisa.masafahdriver.util.GridSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import static com.vavisa.masafahdriver.activities.MainActivity.navigationView;
import static com.vavisa.masafahdriver.util.FragmentUtil.switchFragment;

public class ProfileFragment extends BaseFragment implements View.OnClickListener {

  private View fragment;
  private RecyclerView profileGridView;
  private List<Profile> profileItems;
  private ConstraintLayout profileLayout;
  private Button logout;

  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    if (fragment == null) {
      fragment = inflater.inflate(R.layout.fragment_profile, container, false);

      Toolbar toolbar = fragment.findViewById(R.id.profile_toolbar);
      ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
      getActivity().setTitle("");

      profileLayout = fragment.findViewById(R.id.profile_layout);
      profileGridView = fragment.findViewById(R.id.profile_items);
      logout = fragment.findViewById(R.id.logout_button);

      logout.setOnClickListener(this);
      profileGridView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
      profileGridView.addItemDecoration(new GridSpaceItemDecoration(25));

      profileLayout.post(
          new Runnable() {
            @Override
            public void run() {
              int height = profileLayout.getHeight();
              RelativeLayout.LayoutParams layoutParams =
                  (RelativeLayout.LayoutParams) profileLayout.getLayoutParams();
              layoutParams.topMargin = -(height / 3);
              profileLayout.setLayoutParams(layoutParams);
            }
          });

    } else {
      for (int i = 1; i < navigationView.getMenu().size(); i++) {
        navigationView.getMenu().getItem(i).setChecked(false);
      }
      navigationView.getMenu().getItem(1).setChecked(true);
    }

    setupProfileItems();

    profileGridView.setAdapter(new ProfileAdapter());

    return fragment;
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.logout_button:
        getActivity()
            .getSupportFragmentManager()
            .popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        break;
    }
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
          new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              Fragment fragment = null;
              switch (position) {
                case 0:
                  final DialogPlus dialogPlus =
                      DialogPlus.newDialog(getActivity())
                          .setGravity(Gravity.BOTTOM)
                          .setContentBackgroundResource(R.drawable.rounded_corners_white_filled)
                          .setContentHolder(new ViewHolder(R.layout.profile_view))
                          .create();

                  ImageView close_icon = (ImageView) dialogPlus.findViewById(R.id.close_icon);
                  close_icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { dialogPlus.dismiss(); }});

                  dialogPlus.show();
                  break;

                case 2:
                  fragment = new ShipmentHistoryFragment();
                  switchFragment(
                      getActivity().getSupportFragmentManager(), fragment, "shipmentHistory");
                  break;

                case 4:
                  startActivity(new Intent(getActivity(), WalletActivity.class));
                  break;
              }
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
    profile.setName("My details");

    profileItems.add(profile);

    profile = new Profile();
    profile.setImage(R.drawable.ic_language_primary_24dp);
    profile.setName("Language");

    profileItems.add(profile);

    profile = new Profile();
    profile.setImage(R.drawable.ic_box);
    profile.setName("Shipment history");

    profileItems.add(profile);

    profile = new Profile();
    profile.setImage(R.drawable.ic_document);
    profile.setName("Terms & conditions");

    profileItems.add(profile);

    profile = new Profile();
    profile.setImage(R.drawable.ic_document);
    profile.setName("Wallet");

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
