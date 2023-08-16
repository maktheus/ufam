can(move(Block, From, To), [clear(Block), clear(To), on(Block, From)]) :-
    block(Block),
    object(From),
    object(To),
    To \== Block,
    From \== To,
    Block \== From.
    % adds( Action, Relationships): Action establishes Relationships
    adds( move(X, From, To), [ on(X, To), clear(From)]).
    % deletes( Action, Relationships): Action destroys Relationships
    deletes( move(X, From, To), [ on(X, From), clear(To)]).
    object( X) :-
        place( X)
        ;
        block( X).
    % A blocks world
    block( a).
    block( b).
    block( c).
    place( 1).
    place( 2).
    place( 3).
    place( 4).
    % A state in the blocks world
    %
    %        c
    %        a   b
    %        = = = =
    % place  1 2 3 4
    state1([clear(2), clear(4), clear(b), clear(c), on(a,1), on(b,3), on(c,a)]).
