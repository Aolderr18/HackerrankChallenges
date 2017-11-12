#include <cmath>
#include <cstdio>
#include <vector>
#include <iostream>
#include <algorithm>
using namespace std;

const unsigned width = 10;
const unsigned height = 10;

class CrosswordPuzzle {
protected:
    vector<vector<char >> letterGrid;
    vector<string > words;
private:
    vector<bool > wordAvailable;
    unsigned n;
public:
    CrosswordPuzzle(vector<vector<char >>, vector<string >);
    bool updateLetterGrid(const unsigned &, const unsigned &, const char &);
    bool removeWord(const unsigned &);
    bool addWord(const unsigned &);
    unsigned numberOfAvailableWords() const;
    bool wordIsAvailable(const unsigned &) const;
    vector<vector<char >> getLetterGrid() const;
    vector<string > getWords() const;
    unsigned getN() const;
};

CrosswordPuzzle::CrosswordPuzzle(vector<vector<char >> _letterGrid, vector<string > _words) {
    letterGrid = _letterGrid;
    words = _words;
    n = _words.size();
    for (unsigned z = 0; z < n; z++)
        wordAvailable.push_back(true);
}

bool CrosswordPuzzle::updateLetterGrid(const unsigned & x, const unsigned & y, const char & c) {
    if (letterGrid[x][y] == c)
        return false;
    letterGrid[x][y] = c;
    return true;
}

bool CrosswordPuzzle::removeWord(const unsigned & index) {
    if (!wordAvailable[index])
        return false;
    wordAvailable[index] = false;
    return true;
}

bool CrosswordPuzzle::addWord(const unsigned & index) {
    if (wordAvailable[index])
        return false;
    wordAvailable[index] = true;
    return true;
}

unsigned CrosswordPuzzle::numberOfAvailableWords() const {
    unsigned numAvailable = 0;
    for (unsigned r = 0; r < n; r++)
        if (wordAvailable[r])
            ++numAvailable;
    return numAvailable;
}

bool CrosswordPuzzle::wordIsAvailable(const unsigned & index) const {
    return wordAvailable[index];
}

vector<vector<char >> CrosswordPuzzle::getLetterGrid() const {
    return letterGrid;
}

vector<string > CrosswordPuzzle::getWords() const {
    return words;
}

unsigned CrosswordPuzzle::getN() const {
    return n;
}

bool solveCrosswordPuzzle(CrosswordPuzzle &);

int main() {
    /* Enter your code here. Read input from STDIN. Print output to STDOUT */  
    vector<vector<char >> letterGrid;
    unsigned x, y;
    for (y = 0; y < height; y++) {
        string letterRow;
        cin >> letterRow;
        vector<char > lettersInRow;
        for (x = 0; x < width; x++)
            lettersInRow.push_back(letterRow.at(x));
        letterGrid.push_back(lettersInRow);
    }
    vector<string > words;
    string wordRow;
    cin >> wordRow;
    string currentWord = "";
    for (x = 0; x < wordRow.length(); x++)
        if (wordRow.at(x) == ';') {
            words.push_back(currentWord);
            currentWord = "";
        } else
            currentWord += wordRow.at(x);
    words.push_back(currentWord);
    CrosswordPuzzle unsolved(letterGrid, words);
    bool scp = solveCrosswordPuzzle(unsolved);
    if (scp)
        for (x = 0; x < width; x++) {
            for (y = 0; y < height; y++)
                cout << unsolved.getLetterGrid()[x][y];
            cout << "\n";
        }
    return 0;
}

bool solveCrosswordPuzzle(CrosswordPuzzle & unsolved) {
    unsigned startIndex, endIndex;
    unsigned x, y;
    unsigned p;
    unsigned w;
    bool valid;
    const unsigned n = unsolved.getN();
    for (y = 0; y < height; y++)
        for (p = 0; p < n; p++) {
            if (!unsolved.wordIsAvailable(p))
                continue;
            const unsigned m = unsolved.getWords()[p].length();
            for (startIndex = 0; startIndex <= width - m; startIndex++) {
                valid = true;
                for (w = startIndex; w < startIndex + m; w++) 
                    if (unsolved.getLetterGrid()[w][y] != '-')
                        if (unsolved.getLetterGrid()[w][y] != unsolved.getWords()[p].at(w - startIndex))
                            valid = false;   
                if (startIndex > 0)
                    if (unsolved.getLetterGrid()[startIndex - 1][y] != '+')
                        valid = false;
                if (startIndex + m < width)
                    if (unsolved.getLetterGrid()[startIndex + m][y] != '+')
                        valid = false;
                if (valid) {
                    vector<char > originalLetters;
                    for (w = startIndex; w < startIndex + m; w++) {
                        originalLetters.push_back(unsolved.getLetterGrid()[w][y]);
                        unsolved.updateLetterGrid(w, y, unsolved.getWords()[p].at(w - startIndex));
                    }
                    unsolved.removeWord(p);
                    const bool solved = solveCrosswordPuzzle(unsolved);
                    if (solved)
                        return true;
                    unsolved.addWord(p);
                    for (w = startIndex; w < startIndex + m; w++)
                        unsolved.updateLetterGrid(w, y, originalLetters[w - startIndex]);
                }
            }
        }
    for (x = 0; x < width; x++)
        for (p = 0; p < n; p++) {
            if (!unsolved.wordIsAvailable(p))
                continue;
            const unsigned m = unsolved.getWords()[p].length();
            for (startIndex = 0; startIndex <= height - m; startIndex++) {
                valid = true;
                for (w = startIndex; w < startIndex + m; w++) 
                    if (unsolved.getLetterGrid()[x][w] != '-') 
                        if (unsolved.getLetterGrid()[x][w] != unsolved.getWords()[p].at(w - startIndex))
                            valid = false;
                if (startIndex > 0)
                    if (unsolved.getLetterGrid()[x][startIndex - 1] != '+')
                        valid = false;
                if (startIndex + m < height)
                    if (unsolved.getLetterGrid()[x][startIndex + m] != '+')
                        valid = false;
                if (valid) {
                    vector<char > originalLetters;
                    for (w = startIndex; w < startIndex + m; w++) {
                        originalLetters.push_back(unsolved.getLetterGrid()[x][w - startIndex]);
                        unsolved.updateLetterGrid(x, w, unsolved.getWords()[p].at(w - startIndex));
                    }
                    unsolved.removeWord(p);
                    const bool solved = solveCrosswordPuzzle(unsolved);
                    if (solved)
                        return true;
                    unsolved.addWord(p);
                    for (w = startIndex; w < startIndex + m; w++)
                        unsolved.updateLetterGrid(x, w, originalLetters[w - startIndex]);
            }
        }
    }
    return unsolved.numberOfAvailableWords() == 0;
}
