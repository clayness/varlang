package varlang;

import varlang.Env.ExtendEnv;
import varlang.Env.EmptyEnv;

import java.util.ArrayList;
import java.util.List;

import static varlang.AST.*;
import static varlang.Value.NumVal;
import static varlang.Value.UnitVal;

public class Evaluator implements Visitor<Value> {
    Value valueOf(Program p) {
        return (Value) p.accept(this, new EmptyEnv());
    }

    @Override
    public Value visit(AddExp e, Env env) {
        List<Exp> operands = e.all();
        double result = 0;
        for (Exp exp : operands) {
            NumVal intermediate = (NumVal) exp.accept(this, env); // Dynamic type-checking
            result += intermediate.v(); //Semantics of AddExp in terms of the target language.
        }
        return new NumVal(result);
    }

    @Override
    public Value visit(UnitExp e, Env env) {
        return new UnitVal();
    }

    @Override
    public Value visit(NumExp e, Env env) {
        return new NumVal(e.v());
    }

    @Override
    public Value visit(DivExp e, Env env) {
        List<Exp> operands = e.all();
        NumVal lVal = (NumVal) operands.getFirst().accept(this, env);
        double result = lVal.v();
        for (int i = 1; i < operands.size(); i++) {
            NumVal rVal = (NumVal) operands.get(i).accept(this, env);
            result = result / rVal.v();
        }
        return new NumVal(result);
    }

    @Override
    public Value visit(MultExp e, Env env) {
        List<Exp> operands = e.all();
        double result = 1;
        for (Exp exp : operands) {
            NumVal intermediate = (NumVal) exp.accept(this, env); // Dynamic type-checking
            result *= intermediate.v(); //Semantics of MultExp.
        }
        return new NumVal(result);
    }

    @Override
    public Value visit(Program p, Env env) {
        return (Value) p.e().accept(this, env);
    }

    @Override
    public Value visit(SubExp e, Env env) {
        List<Exp> operands = e.all();
        NumVal lVal = (NumVal) operands.getFirst().accept(this, env);
        double result = lVal.v();
        for (int i = 1; i < operands.size(); i++) {
            NumVal rVal = (NumVal) operands.get(i).accept(this, env);
            result = result - rVal.v();
        }
        return new NumVal(result);
    }

    @Override
    public Value visit(VarExp e, Env env) { // New for varlang
        return env.get(e.name());
    }

    @Override
    public Value visit(LetExp e, Env env) { // New for varlang.
        List<String> names = e.names();
        List<Exp> value_exps = e.value_exps();
        List<Value> values = new ArrayList<>(value_exps.size());

        for (Exp exp : value_exps)
            values.add((Value) exp.accept(this, env));

        Env new_env = env;
        for (int i = 0; i < names.size(); i++)
            new_env = new ExtendEnv(new_env, names.get(i), values.get(i));

        return (Value) e.body().accept(this, new_env);
    }
}
