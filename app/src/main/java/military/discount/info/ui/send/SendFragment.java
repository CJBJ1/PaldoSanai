package military.discount.info.ui.send;

import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import military.discount.info.R;

public class SendFragment extends Fragment {

    private SendViewModel sendViewModel;
    private EditText userId;
    private EditText userPassword;
    private ImageButton loginButton;
    private SignInButton signInButton;

    public static final int RC_SIGN_IN = 1;
    private GoogleSignInClient mgoogleSignInClient;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(SendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_send, container, false);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mgoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        signInButton = (SignInButton) root.findViewById(R.id.google_login);


        userId = (EditText) root.findViewById(R.id.editText_id);
        userPassword = (EditText) root.findViewById(R.id.editText_password);
        loginButton = (ImageButton) root.findViewById(R.id.imageButton_log);



        return root;
    }

}