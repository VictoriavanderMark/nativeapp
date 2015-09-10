package com.example.victoria.week2;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.view.View.OnTouchListener;
import android.widget.TextView;


public class MainActivity extends Activity{

    public ImageView Grid;
    public Tile UpLe;
    public Tile UpCe;
    public Tile UpRi;
    public Tile CeLe;
    public Tile CeCe;
    public Tile CeRi;
    public Tile DoLe;
    public Tile DoCe;
    public Tile DoRi;
    public int moves;
    public boolean won;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moves = 0;
        won = false;
        ImageButton fresh = (ImageButton) findViewById(R.id.refresh);
        UpLe = new Tile((ImageButton) findViewById(R.id.UpLe));
        UpCe = new Tile((ImageButton) findViewById(R.id.UpCe));
        UpRi = new Tile((ImageButton) findViewById(R.id.UpRi));
        CeLe = new Tile((ImageButton) findViewById(R.id.CeLe));
        CeCe = new Tile((ImageButton) findViewById(R.id.CeCe));
        CeRi = new Tile((ImageButton) findViewById(R.id.CeRi));
        DoLe = new Tile((ImageButton) findViewById(R.id.DoLe));
        DoCe = new Tile((ImageButton) findViewById(R.id.DoCe));
        DoRi = new Tile((ImageButton) findViewById(R.id.DoRi));

        UpLe.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!UpLe.isSet() & !won) {
                    int image = pickImage();
                    UpLe.setImage(image);
                    hasWon();
                }
            }
        });

        UpCe.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!UpCe.isSet() & !won) {
                    int image = pickImage();
                    UpCe.setImage(image);
                    hasWon();
                }
            }
        });

        UpRi.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!UpRi.isSet() & !won) {
                    int image = pickImage();
                    UpRi.setImage(image);
                    hasWon();
                }
            }
        });

        CeLe.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!CeLe.isSet() & !won) {
                    int image = pickImage();
                    CeLe.setImage(image);
                    hasWon();
                }
            }
        });

        CeCe.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!CeCe.isSet() & !won) {
                    int image = pickImage();
                    CeCe.setImage(image);
                    hasWon();
                }
            }
        });

        CeRi.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!CeRi.isSet() & !won) {
                    int image = pickImage();
                    CeRi.setImage(image);
                    hasWon();
                }
            }
        });

        DoLe.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!DoLe.isSet() & !won) {
                    int image = pickImage();
                    DoLe.setImage(image);
                    hasWon();
                }
            }
        });

        DoCe.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!DoCe.isSet() & !won) {
                    int image = pickImage();
                    DoCe.setImage(image);
                    hasWon();
                }
            }
        });

        DoRi.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!DoRi.isSet() & !won) {
                    int image = pickImage();
                    DoRi.setImage(image);
                    hasWon();
                }
            }
        });

        fresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public int pickImage() {
        int image;
        System.out.println(moves);
        if(moves % 2 == 0) {
            image =  R.drawable.cross;
        } else {
            image = R.drawable.nodge;
        }
        adaptMoves();
        System.out.println(moves);
        return image;
    }

    public void adaptMoves() {
        moves++;

    }


    public void resetGame() {
        won = false;
        moves = 0;
        int image = R.drawable.base;
        UpLe.reset(image);
        UpCe.reset(image);
        UpRi.reset(image);
        CeLe.reset(image);
        CeCe.reset(image);
        CeRi.reset(image);
        DoLe.reset(image);
        DoCe.reset(image);
        DoRi.reset(image);
        TextView victory =(TextView)findViewById(R.id.victory);
        victory.setText("");
    }

    public void hasWon() {
        if(checkWon()) {
            TextView victory =(TextView)findViewById(R.id.victory);
            String player = getPlayer();
            victory.setText("PLAYER " + player + " WON!");
            won = true;
        }
    }

    public String getPlayer() {
        if(moves % 2 == 0) {
            return "BLUE";
        } else {
            return "RED";
        }
    }

    public boolean checkWon(){
        int ul = UpLe.getImage();
        int uc = UpCe.getImage();
        int ur = UpRi.getImage();
        int cl = CeLe.getImage();
        int cc = CeCe.getImage();
        int cr = CeRi.getImage();
        int dl = DoLe.getImage();
        int dc = DoCe.getImage();
        int dr = DoRi.getImage();
        boolean ulset = UpLe.isSet();
        boolean ucset = UpCe.isSet();
        boolean urset = UpRi.isSet();
        boolean clset = CeLe.isSet();
        boolean ccset = CeCe.isSet();
        boolean crset = CeRi.isSet();
        boolean dlset = DoLe.isSet();
        boolean dcset = DoCe.isSet();
        boolean drset = DoRi.isSet();

        System.out.println(ul + " " + uc + " " + ur);
        if(ulset & ucset & urset & ul == uc && uc == ur ){
            return true;
        }
        else if(clset & ccset & crset & cl == cc && cc == cr ){
            return true;
        }
        else if(dlset & dcset & drset & dl == dc && dc == dr ){
            return true;
        }

        else if(ulset & clset & dlset & ul == cl && cl == dl)  {
            return true;
        }
        else if(ucset & ccset & dcset & uc == cc && cc == dc)  {
            return true;
        }
        else if(urset & crset & drset & ur == cr && cr == dr)  {
            return true;
        }

        else if(ulset & ccset & drset & ul == cc && cc == dr) {
            return true;
        }

        else if(urset & ccset & dlset & ur == cc && cc == dl) {
            return true;
        }
        else {
            return false;
        }
    }

}
