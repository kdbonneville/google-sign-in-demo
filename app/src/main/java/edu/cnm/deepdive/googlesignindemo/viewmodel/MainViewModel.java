package edu.cnm.deepdive.googlesignindemo.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import edu.cnm.deepdive.googlesignindemo.model.User;
import edu.cnm.deepdive.googlesignindemo.service.UserRepository;
import io.reactivex.functions.Consumer;
import org.jetbrains.annotations.NotNull;

public class MainViewModel extends AndroidViewModel {

  private final UserRepository userRepository;
  private final MutableLiveData<GoogleSignInAccount> account;
  private final MutableLiveData<User> user;
  private final MutableLiveData<Throwable> throwable;

  public MainViewModel(
      @NonNull Application application) {
    super(application);
    userRepository = new UserRepository(application);
    account = new MutableLiveData<>();
    user = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
    userFromServer();
  }

  private void userFromServer() {
    userRepository.getUserProfile()
        .subscribe(
            new Consumer<User>() {
              @Override
              public void accept(User value) throws Exception {
                user.postValue(value);
              }
            },
            throwable::postValue
        );
  }
}
