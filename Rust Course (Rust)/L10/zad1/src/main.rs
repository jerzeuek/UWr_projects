fn main() {
}

struct Sudoku {
    data: Vec<Vec<u32>>,
}

impl Sudoku {
    fn check_num_cols_rows(&self) -> bool {
        let cols = self.data.len();
        let cols_sqt = (cols as f64).sqrt() as usize;
        if cols_sqt * cols_sqt != cols {
            return false;
        }

        for i in 0..cols {
            if self.data[i].len() != cols {
                return false;
            }
        }
        return true;
    }

    fn check_rows(&self, nums_set: &Vec<u32>) -> bool {
        for i in 0..self.data.len() {
            if !nums_set.iter().all(|n| self.data[i].contains(n)) {
                return false;
            }
        }
        return true;
    }

    fn check_cols(&self, nums_set: &Vec<u32>) -> bool {
        for i in 0..self.data.len() {
            let col: Vec<u32> = self.data.iter().map(|n| n[i]).collect();
            if !nums_set.iter().all(|n| col.contains(n)) {
                return false;
            }
        }
        return true;
    }

    fn check_squares(&self, nums_set: Vec<u32>, sqrt: usize) -> bool {
        for i in (0..nums_set.len()).step_by(sqrt) {
            for j in (0..nums_set.len()).step_by(sqrt) {
                let square: Vec<u32> = (i..i + sqrt)
                    .flat_map(|n| (j..j + sqrt).map(move |m| self.data[n][m]))
                    .collect();
                if !nums_set.iter().all(|n| square.contains(n)) {
                    return false;
                }
            }
        }
        return true;
    }

    fn is_valid(&self) -> bool {
        let nums_set: Vec<u32> = (1..=self.data.len() as u32).collect();
        let small_square_len = (self.data.len() as f64).sqrt() as usize;

        return self.check_num_cols_rows()
            && self.check_rows(&nums_set)
            && self.check_cols(&nums_set)
            && self.check_squares(nums_set, small_square_len);
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    
    #[test]
    fn sudoku1() {
        let good_sudoku_1 = Sudoku {
            data: vec![
                vec![7, 8, 4, 1, 5, 9, 3, 2, 6],
                vec![5, 3, 9, 6, 7, 2, 8, 4, 1],
                vec![6, 1, 2, 4, 3, 8, 7, 5, 9],
                vec![9, 2, 8, 7, 1, 5, 4, 6, 3],
                vec![3, 5, 7, 8, 4, 6, 1, 9, 2],
                vec![4, 6, 1, 9, 2, 3, 5, 8, 7],
                vec![8, 7, 6, 3, 9, 4, 2, 1, 5],
                vec![2, 4, 3, 5, 6, 1, 9, 7, 8],
                vec![1, 9, 5, 2, 8, 7, 6, 3, 4],
            ],
        };
        assert!(good_sudoku_1.is_valid());
    }

    #[test]
    fn sudoku2() {
        let good_sudoku_2 = Sudoku {
            data: vec![
                vec![1, 4, 2, 3],
                vec![3, 2, 4, 1],
                vec![4, 1, 3, 2],
                vec![2, 3, 1, 4],
            ],
        };
        assert!(good_sudoku_2.is_valid());
    }

    #[test]
    fn sudoku3() {
        let bad_sudoku_1 = Sudoku {
            data: vec![
                vec![1, 2, 3, 4, 5, 6, 7, 8, 9],
                vec![1, 2, 3, 4, 5, 6, 7, 8, 9],
                vec![1, 2, 3, 4, 5, 6, 7, 8, 9],
                vec![1, 2, 3, 4, 5, 6, 7, 8, 9],
                vec![1, 2, 3, 4, 5, 6, 7, 8, 9],
                vec![1, 2, 3, 4, 5, 6, 7, 8, 9],
                vec![1, 2, 3, 4, 5, 6, 7, 8, 9],
                vec![1, 2, 3, 4, 5, 6, 7, 8, 9],
                vec![1, 2, 3, 4, 5, 6, 7, 8, 9],
            ],
        };

        assert!(!bad_sudoku_1.is_valid());
    }

    #[test]
    fn sudoku4() {
        let bad_sudoku_2 = Sudoku {
            data: vec![
                vec![1, 2, 3, 4, 5],
                vec![1, 2, 3, 4],
                vec![1, 2, 3, 4],
                vec![1],
            ],
        };
        assert!(!bad_sudoku_2.is_valid());
    }

  #[test]
   fn sudoku5() {
    let good_sudoku_3 = Sudoku {
      data: vec![
        vec![4, 1, 6, 8, 7, 2, 5, 9, 3],
        vec![7, 5, 2, 9, 6, 3, 8, 1, 4],
        vec![3, 9, 8, 5, 4, 1, 2, 6, 7],
        vec![2, 3, 1, 6, 9, 4, 7, 8, 5],
        vec![6, 8, 5, 1, 2, 7, 4, 3, 9],
        vec![9, 7, 4, 3, 8, 5, 6, 2, 1],
        vec![5, 4, 9, 2, 1, 8, 3, 7, 6],
        vec![8, 6, 3, 7, 5, 9, 1, 4, 2],
        vec![1, 2, 7, 4, 3, 6, 9, 5, 8],
      ],
    };
    assert!(good_sudoku_3.is_valid());
   }
}
