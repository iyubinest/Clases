package co.iyubinest.app;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import co.iyubinest.app.MainActivity.MessageBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;

public class MainActivity extends AppCompatActivity
    implements Callback<MessageBody>, View.OnClickListener {

  private View mainView;

  private View initButton;

  private Api api;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mainView = findViewById(android.R.id.content);
    initButton = findViewById(R.id.login_init);
    initButton.setOnClickListener(this);

    Retrofit retrofit = new Retrofit.Builder().baseUrl("http://demo8720912.mockable.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    api = retrofit.create(Api.class);
  }

  @Override public void onResponse(Call<MessageBody> call, Response<MessageBody> response) {
    Snackbar.make(mainView, response.body().getMsg(), Snackbar.LENGTH_SHORT).show();
  }

  @Override public void onFailure(Call<MessageBody> call, Throwable t) {
    Snackbar.make(mainView, "Error", Snackbar.LENGTH_SHORT).show();
  }

  @Override public void onClick(View view) {
    api.login().enqueue(this);
  }

  interface Api {

    @POST("login") public Call<MessageBody> login();
  }

  class MessageBody {

    private final String msg;

    MessageBody(String msg) {
      this.msg = msg;
    }

    public String getMsg() {
      return msg;
    }
  }
}
