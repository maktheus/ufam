%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%                                  
% A Definition of the blocks' world for planning
% Coder: Edjard Mota       
% Date   : 21/04/2021              
% Source:  Prolog Programming for AI, Ivan Bratko, 
%          4th edition
%          Chapter 17 - Representing actions 
%                       for planning          
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

final([clear(1),clear(2),clear(3),on(d,p([4,6])),on(c,p([d,d])),on(a,c),on(b,c)])
%                                   on(Block,p())
% state1([clear(3),on(c,p([1,2])),on(b,6),on(a,4), on(d,p([a,b]))]).

%----------------------------------------------------------
% Definition of action move(Block, From, To)
% can(Action, Condition): Action possible if Condition true

can(move(Block,p([Oi,Oj]),p([Bi,Bj])),[clear(Block),clear(Bi),clear(Bj),on(Block,p([Oi,Oj]))]):-
    block(Block),           % There is this Block to move
    block(Bi),              % 
    block(Bj),
    Bi \== Block,           % Block cannot be moved to itself
    block(Oi), block(Oj),   % 'From' is a block or place
    Oi \== Bi,              % 'To' is a new position to move
    Oj \== Bj,              % 'To' is a new position to move
    Block \== Oi,
    size(Block,Sb),
    size(Bi,Si),
    size(Bj,Sj),
    SizeTo is Si + Sj,
    Sb =< SizeTo + 1.       % Bloco on the top cannot exceed 1 of To

% This case assume a Block 2 units bigger than the single block B 
% will always be placed in the middle to maintain its centroid 
% aligned with B's centroid

can(move(Block,p([Oi,Oj]),p(B,B)),[clear(Block),clear(B),on(Block,p([Oi,Oj]))]):-
    block(Block),           % There is this Block to move
    block(B),
    B \== Block,            % Block cannot be moved to itself
    block(Oi), block(Oj),   % 'From' is a block or place
    Oi \== B,               % 'To' is a new position to move
    Oj \== B,
    size(Block,SizeB),
    size(B,Sb),
    SizeB =< Sb + 2.

%can(move(Block,p(To,To),p(Bi,Bj)),[clear(Block),clear(Bi),clear(Bj),on(Block,p(Oi,Oj))]):-

can(move(Block,From,To),[clear(Block),clear(To),on(Block,From)]):-
    block(Block),           % There is this Block to move
    object(To),             % 'T' is a block or place
    To \== Block,           % Block cannot be moved to itself
    object(From),           % 'From' is a block or place
    From \== To,            % 'To' is a new position to move
    Block \== From.         % Bloco not moved from itself

%----------------------------------------------------------
% adds(Action, Relationships): Action establishes new Relationships
adds(move(X,From,To),[on(X,To),clear(From)]). 

%----------------------------------------------------------
% deletes(Action, Relationships): Action destroy Relationships
deletes(move(X,From,To),[on(X,From),clear(To)]).

object(X):-
    place(X)
    ;
    block(X).
%----------------------------------------------------------
impossible(on(X,X),_).
impossible(on(X,Y),Goals):-
    member(clear(Y),Goals)
    ;
    member(on(X,Y1),Goals), Y1 \== Y   % Block cannot be in two places
    ;
    member(on(X1,Y), Goals), X1 \== X. % Two blocks cannot be at the same place
impossible(clear(X),Goals):-
    member(on(_,X),Goals).