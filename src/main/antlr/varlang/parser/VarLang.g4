grammar VarLang;

import ArithLang; //Import all rules from ArithLang grammar.
 
 // New elements in the Grammar of this Programming Language
 //  - grammar rules start with lowercase

 exp returns [Exp ast]:
		  v=varexp  { $ast = $v.ast; }
		| n=numexp  { $ast = $n.ast; }
        | a=addexp  { $ast = $a.ast; }
        | s=subexp  { $ast = $s.ast; }
        | m=multexp { $ast = $m.ast; }
        | d=divexp  { $ast = $d.ast; }
        | l=letexp  { $ast = $l.ast; }
        ;

 varexp returns [VarExp ast]:
 		id=Identifier { $ast = new VarExp($id.text); }
 		;

 letexp  returns [LetExp ast]
        locals [
            ArrayList<String> names   = new ArrayList<String>(),
            ArrayList<Exp> value_exps = new ArrayList<Exp>();
        ] :
 		'(' Let
 			'(' ( '(' id=Identifier e=exp ')' { $names.add($id.text); $value_exps.add($e.ast); } )+  ')'
 			body=exp
 			')' { $ast = new LetExp($names, $value_exps, $body.ast); }
 		;

 Let : 'let';

