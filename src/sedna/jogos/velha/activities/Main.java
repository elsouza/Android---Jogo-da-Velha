package sedna.jogos.velha.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class Main extends Activity implements OnClickListener {


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//Associa os listeners de cada botão
		View newButton = findViewById(R.id.new_button);
		newButton.setOnClickListener(this);
		View aboutButton = findViewById(R.id.about_button);
		aboutButton.setOnClickListener(this);
		View exitButton = findViewById(R.id.exit_button);
		exitButton.setOnClickListener(this);
	}


	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.about_button:
			Intent i = new Intent(this, Sobre.class);
			startActivity(i);
			break;
		case R.id.new_button:
			openNewGameDialog();
			break;
		case R.id.exit_button:
			finish();
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) { //implementar para possibilitar um menu na atividade
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true; // indica que o menu será exibido
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) { //verifica qual item do menu foi selecionado
		switch (item.getItemId()) {
		case R.id.settings:
			startActivity(new Intent(this, Preferencias.class));
			return true;
		}
		return false;
	}

	// Pergunta sobre o nível de dificuldade
	private void openNewGameDialog() {
		new AlertDialog.Builder(this).setTitle(R.string.new_game_title)
				.setItems(R.array.difficulty,
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialoginterface, int i) {
								startGame(i);
							}
						}).show();
	}

	// Inicia um novo jogo tendo como parametro o nivel
	private void startGame(int level) {

		Intent intent = new Intent(Main.this, Jogo.class);
		intent.putExtra("level", level);
		startActivity(intent);

	}
}
