//---------------------------------------------------------------------------
#include <vcl\vcl.h>
#pragma hdrstop

#include "Unit2.h"
#include "Unit1.h"
#include "Unit3.h"
//---------------------------------------------------------------------------
#pragma resource "*.dfm"
TForm2 *Form2;
//---------------------------------------------------------------------------
__fastcall TForm2::TForm2(TComponent* Owner)
	: TForm(Owner)
{
}
//---------------------------------------------------------------------------
void __fastcall TForm2::Button1Click(TObject *Sender)
{
 Form2->Hide();Form1->Show();
}
//---------------------------------------------------------------------------
void __fastcall TForm2::Button2Click(TObject *Sender)
{
 Form2->Hide();Form3->Show();
 Form3->VCFormulaOne1->SetActiveCell(1,2);
 Form3->VCFormulaOne1->Text=Form2->Label2->Caption;
 Form3->VCFormulaOne1->SetActiveCell(2,2);
 Form3->VCFormulaOne1->Text=Form2->Label4->Caption;
 Form3->VCFormulaOne1->SetActiveCell(3,2);
 Form3->VCFormulaOne1->Text=Form2->Label6->Caption;
 Form3->VCFormulaOne1->SetActiveCell(4,2);
 Form3->VCFormulaOne1->Text=Form2->Label8->Caption;
 Form3->VCFormulaOne1->SetActiveCell(5,2);
 Form3->VCFormulaOne1->Text=Form2->Label10->Caption;

}
//--------------------------------------------------------------------------- 