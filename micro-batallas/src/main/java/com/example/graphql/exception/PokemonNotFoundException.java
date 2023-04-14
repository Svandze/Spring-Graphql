package com.example.graphql.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class PokemonNotFoundException extends RuntimeException implements GraphQLError {

	private Map<String, Object> extensions = new HashMap<>();

	public PokemonNotFoundException(String message, String invalidPokemonId) {
		super(message);
		extensions.put("invalidPokemonId", invalidPokemonId);
	}

	@Override
	public List<SourceLocation> getLocations() {
		return null;
	}

	@Override
	public Map<String, Object> getExtensions() {
		return extensions;
	}

	@Override
	public ErrorType getErrorType() {
		return ErrorType.DataFetchingException;
	}
}
