package br.com.msm.librarypdf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

public class MainActivity extends AppCompatActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Para um diálogo de progresso simples e moderno, sem custom view complexo:
		MaterialDialog progressDialog = new MaterialDialog(this, MaterialDialog.getDEFAULT_BEHAVIOR())
				.title(null, "Aguarde...")
				 .message(null, "Processando...", null) // Opcional
				.cancelable(true) // Importante para que o usuário não dispense
				.noAutoDismiss(); // Se você for adicionar botões depois e não quiser que fechem o diálogo



		(findViewById(R.id.btnDialog)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


// Para mostrar:
				progressDialog.show();
			}
		});
	}
}
