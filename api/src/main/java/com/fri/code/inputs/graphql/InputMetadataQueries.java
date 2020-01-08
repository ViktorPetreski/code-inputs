package com.fri.code.inputs.graphql;


import com.fri.code.inputs.lib.InputMetadata;
import com.fri.code.inputs.services.beans.InputMetadataBean;
import com.kumuluz.ee.graphql.annotations.GraphQLClass;
import com.kumuluz.ee.graphql.classes.Filter;
import com.kumuluz.ee.graphql.classes.Pagination;
import com.kumuluz.ee.graphql.classes.PaginationWrapper;
import com.kumuluz.ee.graphql.classes.Sort;
import com.kumuluz.ee.graphql.utils.GraphQLUtils;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
@GraphQLClass
public class InputMetadataQueries {
    @Inject
    private InputMetadataBean inputMetadataBean;

    @GraphQLQuery
    public PaginationWrapper<InputMetadata> allInputMetadata(@GraphQLArgument(name = "pagination")Pagination pagination,
                                                             @GraphQLArgument(name = "sort") Sort sort,
                                                             @GraphQLArgument(name = "filter") Filter filter) {
        return GraphQLUtils.process(inputMetadataBean.getInputs(), pagination, sort, filter);
    }

    @GraphQLQuery
    public List<InputMetadata> getInputsForExercise(@GraphQLArgument(name = "exerciseID") Integer exerciseID) {
        return inputMetadataBean.getInputsForExercise(exerciseID);
    }
}
