package com.foxowlet.fol.interpreter.expression;

import com.foxowlet.fol.ast.FormalParameter;
import com.foxowlet.fol.ast.Lambda;
import com.foxowlet.fol.interpreter.InterpretationContext;
import com.foxowlet.fol.interpreter.model.FunctionParameter;
import com.foxowlet.fol.interpreter.model.Function;
import com.foxowlet.fol.interpreter.model.type.TypeDescriptor;
import com.foxowlet.fol.interpreter.type.TypeInterpreter;

import java.util.List;

public class LambdaInterpreter implements ExpressionInterpreter<Lambda> {
    private final TypeInterpreter typeInterpreter;

    public LambdaInterpreter() {
        typeInterpreter = new TypeInterpreter();
    }

    @Override
    public Object interpret(Lambda expression, InterpretationContext context) {
        int id = context.allocateFunction();
        Function function = new Function(id, convertParams(expression.params().subnodes(), context), expression.body());
        context.registerSymbol(String.valueOf(id), function);
        return function;
    }

    private List<FunctionParameter> convertParams(List<FormalParameter> params, InterpretationContext context) {
        return params.stream()
                .map(param -> convertParam(context, param))
                .toList();
    }

    private FunctionParameter convertParam(InterpretationContext context, FormalParameter param) {
        String paramName = param.name().name();
        TypeDescriptor paramType = typeInterpreter.interpret(param.type(), context);
        return new FunctionParameter(paramName, paramType);
    }
}
