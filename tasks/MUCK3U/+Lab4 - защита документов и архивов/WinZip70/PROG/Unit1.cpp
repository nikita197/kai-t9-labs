//---------------------------------------------------------------------------
#include <vcl\vcl.h>
#pragma hdrstop

#include "Unit1.h"
#include "Unit2.h"
//---------------------------------------------------------------------------
#pragma resource "*.dfm"
TForm1 *Form1;
//---------------------------------------------------------------------------
__fastcall TForm1::TForm1(TComponent* Owner)
	: TForm(Owner)
{
}
//---------------------------------------------------------------------------
void __fastcall TForm1::FormShow(TObject *Sender)
{
 ComboBox1->ItemIndex=0;
 ComboBox2->ItemIndex=0;
 ComboBox1Change(Sender);
 if (Query1->Active!=true) Query1->Prepare();
 if (Query2->Active!=true) Query2->Prepare();
}
//---------------------------------------------------------------------------
void __fastcall TForm1::ComboBox1Change(TObject *Sender)
{
 switch (ComboBox1->ItemIndex)
 {
  case 0:ComboBox2->Items->Clear();
  		 ComboBox2->Items->Add("Стол");
         ComboBox2->Items->Add("Кресло офис");
         ComboBox2->Items->Add("Стол офисный");
         ComboBox2->Items->Add("Стул кож");
         ComboBox2->Items->Add("Столик журнал");
         break;
  case 1:ComboBox2->Items->Clear();
  		 ComboBox2->Items->Add("Пальто жен");
         ComboBox2->Items->Add("Пиджак драп");
         break;
  case 2:ComboBox2->Items->Clear();
  		 ComboBox2->Items->Add("Туфли жен");
         ComboBox2->Items->Add("Ботинки муж");
         ComboBox2->Items->Add("Босоножки");
         break;
 }
 ComboBox2->ItemIndex=0;
}
//---------------------------------------------------------------------------
void __fastcall TForm1::Button1Click(TObject *Sender)
{
 Query1->Close();
 Query1->ParamByName("namet")->AsString=ComboBox2->Text;
 Query1->Open();
 DataSource1->DataSet->First();
 int kol=DataSource1->DataSet->Fields[0]->AsInteger;
 int kolv=StrToInt(Edit1->Text);
 if (kolv>kol) {ShowMessage("На складе нет такого объема товара");}
 else {
       Query2->Close();
  	   Query2->ParamByName("namet")->AsString=ComboBox2->Text;
       Query2->Open();
       DataSource2->DataSet->First();
       int price=DataSource2->DataSet->Fields[0]->AsInteger;
       int gprice=price*kolv;
       Form2->Label2->Caption=ComboBox1->Text;
       Form2->Label4->Caption=ComboBox2->Text;
       Form2->Label6->Caption=IntToStr(price);
       Form2->Label8->Caption=Edit1->Text;
       Form2->Label10->Caption=IntToStr(gprice);
       Form1->Hide();Form2->Show();
      }
}
void __fastcall TForm1::Button2Click(TObject *Sender)
{
 Query1->Close();Query2->Close();
 Query1->UnPrepare();Query2->UnPrepare();
 Application->Terminate();
}
//---------------------------------------------------------------------------
