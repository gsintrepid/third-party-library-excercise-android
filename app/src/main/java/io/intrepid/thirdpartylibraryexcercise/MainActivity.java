package io.intrepid.thirdpartylibraryexcercise;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    @BindView(R.id.image_view)
    ImageView imageView;

    private MainPresenter presenter;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        presenter = new MainPresenter();
        presenter.bindView(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.bindView(this);
    }

    @OnClick(R.id.new_cat_button)
    public void onNewCatButtonClick() {
        presenter.onNewCatButtonClick();
    }

    @Override
    public void showLoadingIndicator() {
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading");
        dialog.show();
    }

    @Override
    public void dismissLoadingIndicator() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void loadImageFromUrl(String url) {
        Picasso.with(this)
                .load(url)
                .into(imageView);

        // Dismiss loading indicator right away (does not wait for Picasso callback)
        dismissLoadingIndicator();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unbindView();
    }
}
