package org.jinq.jpa.transform;

import org.jinq.jpa.MetamodelUtil;
import org.jinq.jpa.jpqlquery.ColumnExpressions;
import org.jinq.jpa.jpqlquery.ConstantExpression;
import org.jinq.jpa.jpqlquery.FromAliasExpression;
import org.jinq.jpa.jpqlquery.JPQLQuery;
import org.jinq.jpa.jpqlquery.SelectFromWhere;
import org.jinq.jpa.jpqlquery.SimpleRowReader;

import ch.epfl.labos.iu.orm.queryll2.path.PathAnalysisSimplifier;
import ch.epfl.labos.iu.orm.queryll2.symbolic.TypedValueVisitorException;

public class CountTransform extends JPQLQueryTransform
{
   public CountTransform(MetamodelUtil metamodel)
   {
      super(metamodel);
   }
   
   @Override
   public <U, V> JPQLQuery<U> apply(JPQLQuery<V> query, LambdaInfo lambda) throws QueryTransformException
   {
      if (lambda != null) throw new IllegalArgumentException("lambda should be null");
      
      if (query instanceof SelectFromWhere)
      {
         SelectFromWhere<V> sfw = (SelectFromWhere<V>)query;
         
         // Create the new query, merging in the analysis of the method
         SelectFromWhere<U> toReturn = new SelectFromWhere<U>();
         toReturn.froms.addAll(sfw.froms);
         // TODO: It looks like you can stick anything inside the COUNT(),
         //    but I'm not sure. Why does it even take a parameter there?
         toReturn.cols = ColumnExpressions.singleColumn(
               new SimpleRowReader<>(), 
               new ConstantExpression("COUNT(1)")); 
         toReturn.where = sfw.where;
         return toReturn;
      }
      throw new QueryTransformException("Existing query cannot be transformed further");
   }

}
