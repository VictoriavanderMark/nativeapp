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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moves = 0;
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
                if(!UpLe.isSet()) {
                    int image = pickImage();
                    UpLe.setImage(image);
                    hasWon();
                }
            }
        });

        UpCe.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!UpCe.isSet()) {
                    int image = pickImage();
                    UpCe.setImage(image);
                    hasWon();
                }
            }
        });

        UpRi.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!UpRi.isSet()) {
                    int image = pickImage();
                    UpRi.setImage(image);
                    hasWon();
                }
            }
        });

        CeLe.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!CeLe.isSet()) {
                    int image = pickImage();
                    CeLe.setImage(image);
                    hasWon();
                }
            }
        });

        CeCe.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!CeCe.isSet()) {
                    int image = pickImage();
                    CeCe.setImage(image);
                    hasWon();
                }
            }
        });

        CeRi.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!CeRi.isSet()) {
                    int image = pickImage();
                    CeRi.setImage(image);
                    hasWon();
                }
            }
        });

        DoLe.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!DoLe.isSet()) {
                    int image = pickImage();
                    DoLe.setImage(image);
                    hasWon();
                }
            }
        });

        DoCe.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!DoCe.isSet()) {
                    int image = pickImage();
                    DoCe.setImage(image);
                    hasWon();
                }
            }
        });

        DoRi.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!DoRi.isSet()) {
                    int image = pickImage();
                    DoRi.setImage(image);
                    hasWon();
                }
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


    public void hasWon() {
        if(checkWon()) {
            System.out.println("WONNN");
        }
    }

    public boolean checkWon(){
        int ul = UpLe.getImage();
        int uc = UpCe.getImage();
        int ur = UpRi.getImage();
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
        else if(clset & ccset & crset & CeLe.getImage() == CeCe.getImage() && CeCe.getImage() == CeRi.getImage() ){
            return true;
        }
        else if(dlset & dcset & drset & DoLe.getImage() == DoCe.getImage() && DoCe.getImage() == DoRi.getImage() ){
            return true;
        }

        else if(ulset & clset & dlset & UpLe.getImage() == CeLe.getImage() && CeLe.getImage() == DoLe.getImage())  {
            return true;
        }
        else if(ucset & ccset & dcset & UpCe.getImage() == CeCe.getImage() && CeCe.getImage() == DoCe.getImage())  {
            return true;
        }
        else if(urset & crset & drset & UpRi.getImage() == CeRi.getImage() && CeRi.getImage() == DoRi.getImage())  {
            return true;
        }

        else if(ulset & ccset & drset & UpLe.getImage() == CeCe.getImage() && CeCe.getImage() == DoRi.getImage()) {
            return true;
        }

        else if(urset & ccset & dlset & UpRi.getImage() == CeCe.getImage() && CeCe.getImage() == DoLe.getImage()) {
            return true;
        }
        else {
            return false;
        }
    }

}
