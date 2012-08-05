package sedna.jogos.velha.activities;

import java.util.Random;

import sedna.jogos.velha.negocio.Music;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Atividade Jogo controla o fluxo de uma partida da aplicação.
 */
public class Jogo extends Activity implements OnClickListener {

	public static final int FACIL = 0;
	public static final int MEDIO = 1;
	public static final int DIFICIL = 2;
	private int nivelDificuldade = -1;

	TextView tvDroid = null;
	TextView tvJogador = null;

	private int quantasJogadas = 0;
	private String letra = "";

	private Button[] tabuleiro;
	private Integer vitoriasDroid = 0;
	private Integer vitoriasJogador = 0;

	private static int[][] combinacoesVitoriosas = new int[][] {
			{ 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, // linha vence
			{ 1, 4, 7 }, { 2, 5, 8 }, { 3, 6, 9 }, // coluna vence
			{ 1, 5, 9 }, { 3, 5, 7 } // diagonal vence
	};
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabuleiro);

		Intent intent = getIntent();
		nivelDificuldade = intent.getIntExtra("level", FACIL);

		iniciaTabuleiro();

		// Views para o controle do placar
		tvDroid = (TextView) findViewById(R.id.txtDroid);
		tvJogador = (TextView) findViewById(R.id.txtYou);

		tvDroid.setText("0");
		tvJogador.setText("0");

		
		Toast.makeText(this, "onCreate", 1).show();
	}

	/**
	 * Inicializa o array de botões e seta evento onClickListener para cada
	 * campo do tabuleiro.
	 */
	private void iniciaTabuleiro() {
		
		Music.stop(this);

		tabuleiro = new Button[10];
		for (int i = 1; i < 10; i++)
			tabuleiro[i] = new Button(this);

		tabuleiro[1] = (Button) findViewById(R.id.btM1);
		tabuleiro[2] = (Button) findViewById(R.id.btM2);
		tabuleiro[3] = (Button) findViewById(R.id.btM3);
		tabuleiro[4] = (Button) findViewById(R.id.btM4);
		tabuleiro[5] = (Button) findViewById(R.id.btM5);
		tabuleiro[6] = (Button) findViewById(R.id.btM6);
		tabuleiro[7] = (Button) findViewById(R.id.btM7);
		tabuleiro[8] = (Button) findViewById(R.id.btM8);
		tabuleiro[9] = (Button) findViewById(R.id.btM9);

		for (int i = 1; i < 10; i++) 
			tabuleiro[i].setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		Toast.makeText(this, "onResume", 1).show();
		super.onResume();
	}

	@Override
	protected void onPause() {
		Toast.makeText(this, "onPause", 1).show();
		super.onPause();
		Music.stop(this);
	}

	/**
	 * Indica que o cara jogou
	 * @param v View - Item clicado
	 */
	@Override
	public void onClick(View v) {

		Button btn = encontraCasaPressionada(v);
		jogadaUsuario(btn);

		if (!haVencedor())
			verificaJogadaAndroid();
	}

	private Button encontraCasaPressionada(View v) {
		Button btn = null;
		
		switch (v.getId()) {
			case R.id.btM1:
				btn = (Button) findViewById(R.id.btM1);
				break;
			case R.id.btM2:
				btn = (Button) findViewById(R.id.btM2);
				break;
			case R.id.btM3:
				btn = (Button) findViewById(R.id.btM3);
				break;
			case R.id.btM4:
				btn = (Button) findViewById(R.id.btM4);
				break;
			case R.id.btM5:
				btn = (Button) findViewById(R.id.btM5);
				break;
			case R.id.btM6:
				btn = (Button) findViewById(R.id.btM6);
				break;
			case R.id.btM7:
				btn = (Button) findViewById(R.id.btM7);
				break;
			case R.id.btM8:
				btn = (Button) findViewById(R.id.btM8);
				break;
			case R.id.btM9:
				btn = (Button) findViewById(R.id.btM9);
				break;
		}
		return btn;
	}

	private void jogadaUsuario(Button btn) {
		btn.setText("X");
		btn.setTextColor(Color.GREEN);
		btn.setEnabled(false);
		quantasJogadas++;
	}

	public void verificaJogadaAndroid() {

		quantasJogadas++;

		//No nivel fácil, com menos de 4 jogadas, movimento randômico
		if (nivelDificuldade == FACIL && quantasJogadas < 4) {
			jogadaAleatoria();
			return;
		}

		//No nível médio, só joga randomicamente na primeira vez
		if (nivelDificuldade == MEDIO && quantasJogadas == 2) { 
			jogadaAleatoria();
			return;
		}

		if (!jogadaParaVencer())
			if (!jogadaDefensiva()) 
				jogadaAleatoria();
		
		// Verifica se há vencedor
		haVencedor();
	}

	//TODO  explicar melhor essa parada
	public boolean jogadaParaVencer() {

		boolean jogou = false;

		// Jogada para vencer
		// Verifica se possui 2 casas marcadas nas combinações que vencem marca a terceira

		for (int i = 0; i <= 7; i++) {

			if (tabuleiro[Jogo.combinacoesVitoriosas[i][0]].getText().equals("O") &&
				tabuleiro[Jogo.combinacoesVitoriosas[i][0]].getText().equals(
				tabuleiro[Jogo.combinacoesVitoriosas[i][1]].getText()) &&
				tabuleiro[Jogo.combinacoesVitoriosas[i][2]].getText().equals("")) {

				jogadaAndroid(tabuleiro[Jogo.combinacoesVitoriosas[i][2]]);
				jogou = true;
				break;
			}

			if (tabuleiro[Jogo.combinacoesVitoriosas[i][0]].getText().equals("O") &&
				tabuleiro[Jogo.combinacoesVitoriosas[i][0]].getText().equals(
				tabuleiro[Jogo.combinacoesVitoriosas[i][2]].getText()) &&
				tabuleiro[Jogo.combinacoesVitoriosas[i][1]].getText().equals("")) {

				jogadaAndroid(tabuleiro[Jogo.combinacoesVitoriosas[i][1]]);
				jogou = true;
				break;
			}

			if (tabuleiro[Jogo.combinacoesVitoriosas[i][1]].getText().equals("O") &&
				tabuleiro[Jogo.combinacoesVitoriosas[i][1]].getText().equals(
				tabuleiro[Jogo.combinacoesVitoriosas[i][2]].getText()) &&
				tabuleiro[Jogo.combinacoesVitoriosas[i][0]].getText().equals("")) {

				jogadaAndroid(tabuleiro[Jogo.combinacoesVitoriosas[i][0]]);
				jogou = true;
				break;
			}
		}

		return jogou;
	}

	private boolean jogadaDefensiva() {

		boolean jogou = false;

		// Jogada defensiva
		// Verifica se possui 2 casas marcadas nas combinações que vencem, e
		// marca a terceira para anular a jogada

		for (int i = 0; i <= 7; i++) {

			if (tabuleiro[Jogo.combinacoesVitoriosas[i][0]].getText().equals("X") &&
				tabuleiro[Jogo.combinacoesVitoriosas[i][0]].getText().equals(
				tabuleiro[Jogo.combinacoesVitoriosas[i][1]].getText()) &&
				tabuleiro[Jogo.combinacoesVitoriosas[i][2]].getText().equals("")) {

				jogadaAndroid(tabuleiro[Jogo.combinacoesVitoriosas[i][2]]);
				jogou = true;
				break;
			}

			if (tabuleiro[Jogo.combinacoesVitoriosas[i][0]].getText().equals("X") &&
				tabuleiro[Jogo.combinacoesVitoriosas[i][0]].getText().equals(
				tabuleiro[Jogo.combinacoesVitoriosas[i][2]].getText()) &&
				tabuleiro[Jogo.combinacoesVitoriosas[i][1]].getText().equals("")) {

				jogadaAndroid(tabuleiro[Jogo.combinacoesVitoriosas[i][1]]);
				jogou = true;
				break;
			}

			if (tabuleiro[Jogo.combinacoesVitoriosas[i][1]].getText().equals("X") &&
				tabuleiro[Jogo.combinacoesVitoriosas[i][1]].getText().equals(
				tabuleiro[Jogo.combinacoesVitoriosas[i][2]].getText()) &&
				tabuleiro[Jogo.combinacoesVitoriosas[i][0]].getText().equals("")) {

				jogadaAndroid(tabuleiro[Jogo.combinacoesVitoriosas[i][0]]);
				jogou = true;
				break;
			}
		}

		return jogou;
	}

	private void jogadaAndroid(Button casa) {
		casa.setTextColor(Color.BLUE);
		casa.setText("O");
		casa.setEnabled(false);
	}

	private void jogadaAleatoria() {

		Random x = new Random();
		int y = 1 + x.nextInt(9);

		if (y > 9)
			jogadaAleatoria();
		else {

			if (tabuleiro[y].getText().length() > 0)
				jogadaAleatoria();
			else
				jogadaAndroid(tabuleiro[y]);
		}
	}

	public boolean haVencedor() {

		boolean haVencedor = false;

		// Verifica se houve vencedor
		for (int i = 0; i <= 7 && !haVencedor; i++) {

			if (tabuleiro[Jogo.combinacoesVitoriosas[i][0]].getText().equals(
				tabuleiro[Jogo.combinacoesVitoriosas[i][1]].getText()) &&
				tabuleiro[Jogo.combinacoesVitoriosas[i][1]].getText().equals(
				tabuleiro[Jogo.combinacoesVitoriosas[i][2]].getText()) &&
				!tabuleiro[Jogo.combinacoesVitoriosas[i][0]].getText().equals("")) {

				haVencedor = true;

				if (tabuleiro[Jogo.combinacoesVitoriosas[i][0]].getText().equals("X")) {
					letra = "Você";
					vitoriasJogador++;
				} else {
					letra = "Android";
					vitoriasDroid++;
					
					SharedPreferences prefs = 
						PreferenceManager.getDefaultSharedPreferences(this);

					if (prefs.getBoolean("music", false))
						Music.play(this, R.raw.main);
				}
			}
		}

		mensagemResultado(haVencedor);
		
		return haVencedor;
	}

	private void mensagemResultado(boolean haVencedor) {

		if (haVencedor) {
			new AlertDialog.Builder(Jogo.this).setTitle("Jogo da Velha!").
				setMessage(letra + " venceu!").
				setNeutralButton("OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							limpaJogo();
						}
					}).show();
			
		} else if (quantasJogadas >= 9 && !haVencedor) {
			new AlertDialog.Builder(Jogo.this).setTitle("Jogo da Velha!").
				setMessage("Empate!").
				setNeutralButton("OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int which) { }
					}).show();

			limpaJogo();
		}
	}

	/**
	 * Inicializa as variáveis do jogo
	 */
	public void limpaJogo() {
		
		// Reseta status dos botões
		for (int i = 1; i < 10; i++) {
			tabuleiro[i].setText("");
			tabuleiro[i].setEnabled(true);
		}

		// Reseta variáveis
		quantasJogadas = 0;
		tvDroid.setText(vitoriasDroid.toString());
		tvJogador.setText(vitoriasJogador.toString());
	}

}