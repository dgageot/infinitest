package org.infinitest.parser;

import java.util.List;
import java.util.Set;

import org.jgrapht.DirectedGraph;
import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

class JavaClassGraph
{
    private final Set<JavaClass> vertices = Sets.newHashSet();

    private final DirectedGraph<JavaClass, DefaultEdge> delegate = new DefaultDirectedGraph<JavaClass, DefaultEdge>(
                    new EdgeFactory<JavaClass, DefaultEdge>()
                    {
                        public DefaultEdge createEdge(JavaClass sourceVertex, JavaClass targetVertex)
                        {
                            return new DefaultEdge();
                        }
                    });

    public void addEdge(JavaClass a, JavaClass b)
    {
        delegate.addEdge(a, b);
    }

    public boolean addVertex(JavaClass a)
    {
        if (!vertices.add(a))
        {
            return false;
        }
        return delegate.addVertex(a);
    }

    public void removeVertex(JavaClass a)
    {
        if (vertices.remove(a))
        {
            delegate.removeVertex(a);
        }
    }

    public boolean containsVertex(JavaClass a)
    {
        return vertices.contains(a);
    }

    public Set<JavaClass> vertexSet()
    {
        return vertices;
    }

    public List<JavaClass> predecessorListOf(JavaClass vertex)
    {
        List<JavaClass> predecessors = Lists.newArrayList();

        for (DefaultEdge e : delegate.incomingEdgesOf(vertex))
        {
            predecessors.add(getOppositeVertex(e, vertex));
        }

        return predecessors;
    }

    private JavaClass getOppositeVertex(DefaultEdge e, JavaClass v)
    {
        JavaClass source = delegate.getEdgeSource(e);
        JavaClass target = delegate.getEdgeTarget(e);
        if (v.equals(source))
        {
            return target;
        }
        if (v.equals(target))
        {
            return source;
        }

        throw new IllegalArgumentException("no such vertex");
    }
}