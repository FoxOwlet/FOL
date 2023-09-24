package com.foxowlet.fol.interpreter.scope;

import com.foxowlet.fol.interpreter.exception.DuplicateSymbolException;
import com.foxowlet.fol.interpreter.exception.UndefinedSymbolException;

import java.util.*;

public class LookupScope {
    private final Stack<Map<String, Object>> lookupStack;

    public LookupScope() {
        lookupStack = new Stack<>();
        lookupStack.add(new HashMap<>());
    }

    public void enterScope() {
        lookupStack.push(new HashMap<>());
    }

    public void exitScope() {
        lookupStack.pop();
    }

    public void registerSymbol(String name, Object value) {
        Map<String, Object> symbolMap = lookupStack.peek();
        if (symbolMap.containsKey(name)) {
            throw new DuplicateSymbolException(name);
        }
        symbolMap.put(name, value);
    }

    public Object lookupSymbol(String name) {
        ListIterator<Map<String, Object>> iterator = lookupStack.listIterator(lookupStack.size());
        while (iterator.hasPrevious()) {
            Object value = iterator.previous().get(name);
            if (value != null) {
                return value;
            }
        }
        throw new UndefinedSymbolException(name);
    }
}
