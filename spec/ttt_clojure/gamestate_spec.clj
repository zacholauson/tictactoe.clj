(ns ttt-clojure.gamestate-spec
  (:require [speclj.core :refer :all]
            [ttt-clojure.gamestate :refer :all]))

(describe "first-move?"
  (let [gamestate {:board [:- :- :- :- :- :- :- :- :-]}]
    (it "should return true if the game is brand new || no moves have been taken"
      (should= true (first-move? gamestate)))))

(describe "xs-turn?"
  (it "should evaluate the given gamestate and return true if its x's turn and false if not"
    (should= true  (xs-turn? {:board [:- :- :- :- :- :- :- :- :-]}))
    (should= true  (xs-turn? {:board [:x :o :x :o :- :- :- :- :-]}))
    (should= true  (xs-turn? {:board [:x :o :x :o :o :- :- :- :-]}))
    (should= false (xs-turn? {:board [:x :o :x :o :x :- :- :- :-]}))))

(describe "os-turn?"
  (it "should evaluate the given gamestate and return true if its o's turn and false if not"
    (should= false (os-turn? {:board [:- :- :- :- :- :- :- :- :-]}))
    (should= true  (os-turn? {:board [:x :o :x :o :x :- :- :- :-]}))))

(describe "turn"
  (it "should return :o if there is an greater number of :x's than :o's"
    (should= :o (turn {:board [:x :o :x :- :- :- :- :- :-]})))
  (it "should return :o if there is an lesser or equal number of :x's than :o's"
    (should= :x (turn {:board [:x :o :x :o :- :- :- :- :-]}))))

(describe "computers-turn?"
  (it "should return true if its the computers turn based on the gamestate"
    (should= true (computers-turn? {:board [:x :o :x :o :- :- :- :- :-] :computer :x}))
    (should= true (computers-turn? {:board [:x :o :x :o :x :- :- :- :-] :computer :o}))))

(describe "winning-lines"
  (it "should return a vector of all winning lines with whatever piece is in each position"
    (should= [[:- :- :-] [:- :- :-] [:- :- :-] [:- :- :-] [:- :- :-] [:- :- :-] [:- :- :-] [:- :- :-]]
             (winning-lines {:board [:- :- :- :- :- :- :- :- :-]}))

    (should= [[:x :x :o] [:o :o :-] [:- :- :-] [:x :o :-] [:x :o :-] [:o :- :-] [:x :o :-] [:o :o :-]]
             (winning-lines {:board [:x :x :o :o :o :- :- :- :-]}))))

(describe "win?"
  (context "x won"
    (let [gamestate {:board [:- :- :- :- :- :- :x :x :x]}]
      (it "should return true if 'x' has won"
        (should= true (win? gamestate :x))))))
  (context "o won"
    (let [gamestate {:board [:- :o :- :- :o :- :x :o :x]}]
      (it "should return true if 'o' has won"
        (should= true (win? gamestate :o)))))

(describe "tied?"
  (let [gamestate {:board [:x :o :o :o :x :x :x :x :o]}]
    (it "should return true when no one has won and no more spaces are available"
        (should= true (tied? gamestate)))))

(describe "game-over?"
  (context "x won"
    (let [gamestate {:board [:x :x :x :o :- :- :o :- :-]}]
      (it "should return true when x has won"
        (should= true (game-over? gamestate)))))
  (context "o won"
    (let [gamestate {:board [:o :o :o :x :- :- :x :- :-]}]
      (it "should return true when o has won"
        (should= true (game-over? gamestate)))))
  (context "tied game"
    (let [gamestate {:board [:x :o :o :o :x :x :x :x :o]}]
      (it "should return true when the game has tied"
        (should= true (game-over? gamestate)))))
  (context "game not over"
    (let [gamestate {:board [:- :- :- :- :- :- :- :- :-]}]
      (it "should return false when the game is not over"
        (should= false (game-over? gamestate))))))

(describe "possible-moves"
  (it "should return a list of indexes for the possible moves"
    (should= [0 1 2 3 4 5 6 7 8] (possible-moves {:board [:- :- :- :- :- :- :- :- :-]}))
    (should= [4 5 7 8]           (possible-moves {:board [:o :o :o :x :- :- :x :- :-]}))))

(describe "other-turn"
  (it "should return :o when its :x's turn"
    (should= :o (other-turn {:board [:x :o :o :- :- :- :- :- :-]})))
  (it "should return :x when its :o's turn"
    (should= :x (other-turn {:board [:o :x :x :x :o :- :- :- :-]}))))

(describe "add-play-to-board"
  (it "should return an updated board with the current players turn on the board"
    (should= [:x :- :- :- :- :- :- :- :-] (add-play-to-board {:board [:- :- :- :- :- :- :- :- :-]} 0))))

(describe "make-next-move"
  (it "should take the next move index and put the current players marker on the board and swap to the other players turn"
    (should= {:board [:x :- :- :- :- :- :- :- :-]
              :computer :o
              :options {:difficulty :unbeatable}} (move {:board [:- :- :- :- :- :- :- :- :-]
                                                         :computer :o
                                                         :options {:difficulty :unbeatable}} 0))))
