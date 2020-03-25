package com.jordanec.graphql_sample.graphql;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import graphql.GraphQL;
import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@Component
public class GraphQLProvider
{
    private GraphQL graphQL;

    @Autowired
    GraphQLDataFetchers graphQLDataFetchers;

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }

    @PostConstruct
    public void init() throws IOException
    {
        URL url = Resources.getResource("schema.graphqls");
        String sdl = Resources.toString(url, Charsets.UTF_8);
        GraphQLSchema graphQLSchema = buildSchema(sdl);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, buildWiring());
    }

    private RuntimeWiring buildWiring()
    {
        RuntimeWiring.Builder builder = RuntimeWiring.newRuntimeWiring()
                .scalar(ExtendedScalars.DateTime)
                .scalar(ExtendedScalars.Date)
                .scalar(ExtendedScalars.Time)
                .type(newTypeWiring("Query")
                        .dataFetcher("vehicles", graphQLDataFetchers.getVehicles())
                        .dataFetcher("allVehicles", graphQLDataFetchers.allVehicles())
                        .dataFetcher("allTypes", graphQLDataFetchers.allTypes())
                        .dataFetcher("vehicle", graphQLDataFetchers.getVehicle())
                        .dataFetcher("type", graphQLDataFetchers.type())
                )
                .type(newTypeWiring("Mutation")
                        .dataFetcher("createVehicle", graphQLDataFetchers.createVehicle())
                        .dataFetcher("updateVehicle", graphQLDataFetchers.updateVehicle())
                        .dataFetcher("saveVehicle", graphQLDataFetchers.saveVehicle())
                        .dataFetcher("createType", graphQLDataFetchers.createType())
                );
        return builder.build();
    }
}
