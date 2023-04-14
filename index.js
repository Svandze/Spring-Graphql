const fetch = require("node-fetch");
const { ApolloServer, gql } = require("apollo-server");

const typeDefs = gql`
  type Entrenador {
    entrenadorId: ID!
    nombre: String!
    apellido: String!
    pokemons: [Pokemon]!
  }

  type BatallaPokemon{
    batallaId: ID
    ganador: Pokemon!
    pokemon1: Pokemon!
    pokemon2: Pokemon!
  }

  type Pokemon {
    pokemonId: ID!
    nombre: String!
    tipo: String!
    nivel: Int
    entrenador: Entrenador
  }

  type Query {
    findAllEntrenadores: [Entrenador]!
    countEntrenadores: Int!
    findAllPokemons: [Pokemon]!
    findAllPokemonsById(pokemonIds: [ID!]): [Pokemon]!
    countPokemons: Int!
  }

  type Mutation {
    newEntrenador(nombre: String!, apellido: String!): Entrenador!
    findRandomPokemons: [Pokemon]!
    batallaPokemon: BatallaPokemon!
    newPokemon(nombre: String!, tipo: String!, nivel: Int!, entrenador: ID!): Pokemon!
    borrarPokemon(id: ID!): Boolean
    actualizarNivel(nivel: Int!, id: ID!): Pokemon!
  }
`;

const resolvers = {
  Query: {
    findAllEntrenadores:() => fetchEntrenadores(),
    countEntrenadores: () => countEntrenadores(),
    findAllPokemons: () => fetchPokemons(),
    findAllPokemonsById: (parent, args) => fetchPokemonsById(args),
    countPokemons: () => countPokemons(),
  },
  Mutation: {
    newEntrenador: (parent, args) => createEntrenador(args),
    newPokemon: (parent, args) => createPokemon(args),
    borrarPokemon: (parent, args) => deletePokemon(args),
    actualizarNivel: (parent, args) => updatePokemon(args),
    batallaPokemon: (parent, args) => batallaPokemon(args),
  },
  Entrenador: {
    pokemons: (parent) => parent.pokemons,
  },
};

const server = new ApolloServer({ typeDefs, resolvers });

server.listen().then(({ url }) => {
  console.log(`🚀  Servidor listo ${url}`);
});

function batallaPokemon() {
  return fetch("http://localhost:8088/apis/graphql", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ query: "mutation { batallaPokemon { batallaId ganador { pokemonId nombre tipo nivel } pokemon1 { pokemonId nombre tipo nivel } pokemon2 { pokemonId nombre tipo nivel } } }" })
  })
    .then(res => res.json())
    .then(json => json.data.batallaPokemon);
}

function countEntrenadores() {
  return fetch("http://localhost:8087/apis/graphql", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ query: "{ countEntrenadores }" })
  })
    .then(res => res.json())
    .then(json => json.data.countEntrenadores);
}

function fetchEntrenadores() {
  return fetch("http://localhost:8087/apis/graphql", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ query: "{ findAllEntrenadores { entrenadorId nombre apellido } }" })
  })
    .then(res => res.json())
    .then(json => json.data.findAllEntrenadores);
}

async function createEntrenador(args) {
  const { nombre, apellido } = args;
  const newEntrenador = { nombre, apellido };

  try {
    const response = await fetch("http://localhost:8087/apis/graphql", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        query: `mutation { 
          newEntrenador(nombre: "${nombre}", apellido: "${apellido}") { 
            entrenadorId 
            nombre 
            apellido 
          } 
        }`
      })
    });

    const json = await response.json();

    if (json.errors) {
      throw new Error(json.errors[0].message);
    }

    return json.data.newEntrenador;
  } catch (error) {
    console.log(error);
    return null;
  }
}

function fetchPokemons() {
  return fetch("http://localhost:8087/apis/graphql", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ query: "{ findAllPokemons { pokemonId nombre tipo nivel entrenador { entrenadorId nombre apellido } } }" })
  })
    .then(res => res.json())
    .then(json => json.data.findAllPokemons);
}

function fetchPokemonsById(args) {
  const { pokemonIds } = args;
  const query = `{ findAllPokemonsById(pokemonIds: [${pokemonIds.map(id => `"${id}"`).join(',')}]) { pokemonId nombre tipo nivel entrenador { entrenadorId nombre apellido } } }`;

  return fetch("http://localhost:8087/apis/graphql", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ query })
  })
    .then(res => res.json())
    .then(json => json.data.findAllPokemonsById);
}

function countPokemons() {
  return fetch("http://localhost:8087/apis/graphql", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ query: "{ countPokemons }" })
  })
    .then(res => res.json())
    .then(json => json.data.countPokemons);
}

async function createPokemon(args) {
  const { nombre, tipo, nivel, entrenador } = args;
  const newPokemon = { nombre, tipo, nivel, entrenadorId: entrenador };

  try {
    const response = await fetch("http://localhost:8087/apis/graphql", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        query: `mutation { 
          newPokemon(nombre: "${nombre}", tipo: "${tipo}", nivel: ${nivel}, entrenador: "${entrenador}") { 
            pokemonId 
            nombre 
            tipo 
            nivel 
            entrenador { 
              entrenadorId 
              nombre 
              apellido 
            } 
          } 
        }`
      })
    });

    const json = await response.json();

    if (json.errors) {
      throw new Error(json.errors[0].message);
    }

    return json.data.newPokemon;
  } catch (error) {
    console.log(error);
    return null;
  }
}

async function deletePokemon(args) {
  const { id } = args;

  try {
    const response = await fetch("http://localhost:8087/apis/graphql", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        query: `mutation { 
          deletePokemon(id: "${id}") 
        }`
      })
    });

    const json = await response.json();

    if (json.errors) {
      throw new Error(json.errors[0].message);
    }

    return json.data.deletePokemon;
  } catch (error) {
    console.log(error);
    return false;
  }
}

async function deletePokemon(args) {
  const { id } = args;

  try {
    const response = await fetch("http://localhost:8087/apis/graphql", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        query: `mutation { 
          borrarPokemon(id: "${id}")
        }`
      })
    });

    const json = await response.json();

    if (json.errors) {
      throw new Error(json.errors[0].message);
    }

    return true;
  } catch (error) {
    console.log(error);
    return false;
  }
}

async function updatePokemon(args) {
  const { id, nivel } = args;

  try {
    const response = await fetch("http://localhost:8087/apis/graphql", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        query: `mutation { 
          actualizarNivel(id: "${id}", nivel: ${nivel}) { 
            pokemonId 
            nombre 
            tipo 
            nivel 
            entrenador { 
              entrenadorId 
              nombre 
              apellido 
            } 
          } 
        }`
      })
    });

    const json = await response.json();

    if (json.errors) {
      throw new Error(json.errors[0].message);
    }

    return json.data.actualizarNivel;
  } catch (error) {
    console.log(error);
    return null;
  }
}

function fetchEntrenadorById(id) {
  return fetch("http://localhost:8087/apis/graphql", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ query: `{ findEntrenadorById(entrenadorId: "${id}") { entrenadorId nombre apellido } }` })
  })
    .then(res => res.json())
    .then(json => json.data.findEntrenadorById);
}
function fetchCharacters() {
 
  return fetch("http://localhost:8088/apis/graphql")
    .then(res => res.json())
    .then(json => json.results);
}