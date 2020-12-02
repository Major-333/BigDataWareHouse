"""
A union-find disjoint set data structure.
"""

from __future__ import (
    absolute_import, division, print_function, unicode_literals,
)

import numpy as np


class UnionFind(object):

    def __init__(self, elements=None):
        self.n_elts = 0  # current num of elements
        self.n_comps = 0  # the number of disjoint sets or components
        self._next = 0  # next available id
        self._elts = []  # the elements
        self._indx = {}  #  dict mapping elt -> index in _elts
        self._par = []  # parent: for the internal tree structure
        self._siz = []  # size of the component - correct only for roots

        if elements is None:
            elements = []
        for elt in elements:
            self.add(elt)

    def __repr__(self):
        return  (
            '<UnionFind:\n\telts={},\n\tsiz={},\n\tpar={},\nn_elts={},n_comps={}>'
            .format(
                self._elts,
                self._siz,
                self._par,
                self.n_elts,
                self.n_comps,
            ))

    def __len__(self):
        return self.n_elts

    def __contains__(self, x):
        return x in self._indx

    def __getitem__(self, index):
        if index < 0 or index >= self._next:
            raise IndexError('index {} is out of bound'.format(index))
        return self._elts[index]

    def __setitem__(self, index, x):
        if index < 0 or index >= self._next:
            raise IndexError('index {} is out of bound'.format(index))
        self._elts[index] = x

    def add(self, x):
        if x in self:
            return
        self._elts.append(x)
        self._indx[x] = self._next
        self._par.append(self._next)
        self._siz.append(1)
        self._next += 1
        self.n_elts += 1
        self.n_comps += 1

    def find(self, x):
        if x not in self._indx:
            raise ValueError('{} is not an element'.format(x))

        p = self._indx[x]
        while p != self._par[p]:
            # path compression
            q = self._par[p]
            self._par[p] = self._par[q]
            p = q
        return p

    def connected(self, x, y):
        return self.find(x) == self.find(y)

    def union(self, x, y):
        # Initialize if they are not already in the collection
        for elt in [x, y]:
            if elt not in self:
                self.add(elt)

        xroot = self.find(x)
        yroot = self.find(y)
        if xroot == yroot:
            return
        if self._siz[xroot] < self._siz[yroot]:
            self._par[xroot] = yroot
            self._siz[yroot] += self._siz[xroot]
        else:
            self._par[yroot] = xroot
            self._siz[xroot] += self._siz[yroot]
        self.n_comps -= 1

    def component(self, x):
        if x not in self:
            raise ValueError('{} is not an element'.format(x))
        elts = np.array(self._elts)
        vfind = np.vectorize(self.find)
        roots = vfind(elts)
        return set(elts[roots == self.find(x)])

    def components(self):
        elts = np.array(self._elts)
        vfind = np.vectorize(self.find)
        roots = vfind(elts)
        distinct_roots = set(roots)
        return [set(elts[roots == root]) for root in distinct_roots]

    def component_mapping(self):
        elts = np.array(self._elts)
        vfind = np.vectorize(self.find)
        roots = vfind(elts)
        distinct_roots = set(roots)
        comps = {}
        for root in distinct_roots:
            mask = (roots == root)
            comp = set(elts[mask])
            comps.update({x: comp for x in comp})
        return comps