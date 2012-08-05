package sedna.jogos.velha.activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Preferencias extends PreferenceActivity {
	
	/*
	 * Na versão HONECOMB do Android, esta é uma velha forma de fazer
	 * preferências. A nova forma de criar preferências é através da classe
	 * android.preference.PreferenceFragment, para maiores detalhes consulte a
	 * documentação disponível em: http://developer.android.com/reference/
	 * android/preference/PreferenceFragment.html
	 */
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      addPreferencesFromResource(R.xml.settings);
   }  
   
   /* para usar, fazer 
    * 
    * SharedPreferences sp=PreferenceManager.
getDefaultSharedPreferences(context);
String value=sp.getString("key","defaultvalue");
    */

}
