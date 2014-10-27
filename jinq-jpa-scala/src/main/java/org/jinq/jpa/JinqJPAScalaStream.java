package org.jinq.jpa;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.jinq.jpa.scala.JavaToScalaConverters;
import org.jinq.orm.internal.QueryComposer;
import org.jinq.orm.stream.InQueryStreamSource;
import org.jinq.orm.stream.scala.JinqScalaStream;

import scala.Function1;
import scala.collection.immutable.List;


public class JinqJPAScalaStream<T> implements JinqScalaStream<T>
{
   QueryComposer<T> queryComposer;
   InQueryStreamSource inQueryStreamSource;
   public JinqJPAScalaStream(QueryComposer<T> query)
   {
      this(query, null);
   }
   
   public JinqJPAScalaStream(QueryComposer<T> query, InQueryStreamSource inQueryStreamSource)
   {
      this.inQueryStreamSource = inQueryStreamSource;
      this.queryComposer = query;
   }

   @Override
   public JinqJPAScalaStream<T> where(Function1<T, Object> fn)
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public <U> JinqJPAScalaStream<U> select(Function1<T, U> fn)
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public List<T> toList()
   {
      return JavaToScalaConverters.javaListToList(
            StreamSupport.stream(
                  Spliterators.spliteratorUnknownSize(
                        queryComposer.executeAndReturnResultIterator( err -> {} ), 
                        Spliterator.CONCURRENT), 
                  false).collect(Collectors.toList()));
   }

}