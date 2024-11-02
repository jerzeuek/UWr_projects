fn main() {
}

pub fn execute(code: &str) -> String {
    fn get_num_pos(mut j: usize, code: &str) -> (usize, u32) {
        j += 1;
        let mut count = 0;
        while j < code.len() && code.chars().nth(j).unwrap().is_digit(10) {
            count = count * 10 + code.chars().nth(j).unwrap().to_digit(10).unwrap();
            j += 1;
        }
        count = count.max(1);
        return (j, count);
    }

    let mut positions = vec![(0, 0)];
    let (mut pos_x, mut pos_y) = (0, 0);
    let (mut dir_x, mut dir_y) = (1, 0);
    let rotations = vec![(1, 0), (0, -1), (-1, 0), (0, 1)];

    let mut i = 0;

    while i < code.len() {
        let count: u32;
        match code.chars().nth(i).unwrap() {
            'F' => {
                (i, count) = get_num_pos(i, code);
                for _j in 0..count {
                    pos_x += dir_x;
                    pos_y += dir_y;
                    positions.push((pos_x, pos_y));
                }
            }
            'L' => {
                (i, count) = get_num_pos(i, code);
                (dir_x, dir_y) =
                    rotations[(rotations.iter().position(|&x| x == (dir_x, dir_y)).unwrap()
                        + count as usize)
                        % 4]
            }
            'R' => {
                (i, count) = get_num_pos(i, code);
                (dir_x, dir_y) =
                    rotations[(rotations.iter().position(|&x| x == (dir_x, dir_y)).unwrap() as isize - count as isize) as usize
                        % 4]
            }
            _ => panic!("Wrong command!"),
        }
    }

    let min_x = positions.iter().min_by_key(|&&x| x.0).unwrap().0;
    let max_x = positions.iter().max_by_key(|&&x| x.0).unwrap().0;
    let min_y = positions.iter().min_by_key(|&&x| x.1).unwrap().1;
    let max_y = positions.iter().max_by_key(|&&x| x.1).unwrap().1;

    let mut path: String = String::new();

    for i in min_y..max_y + 1 {
        for j in min_x..max_x + 1 {
            if positions.contains(&(j, i)) {
                path += "*";
            } else {
                path += " ";
            }
        }
        if i < max_y {
            path += "\r\n";
        }
    }
    return path;
}

#[cfg(test)]
mod tests {
    use super::execute;
    #[test]
    fn examples_in_description1() {
        assert_eq!(execute(""), "*");
    }

    #[test]
    fn examples_in_description2() {
        assert_eq!(execute("FFFFF"), "******");
    }

    #[test]
    fn examples_in_description3() {
        assert_eq!(
            execute("FFFFFLFFFFFLFFFFFLFFFFFL"),
            "******\r\n*    *\r\n*    *\r\n*    *\r\n*    *\r\n******",
        );
    }

    #[test]
    fn examples_in_description4() {
        assert_eq!(
            execute("LF5RF3RF3RF7"),
            "    ****\r\n    *  *\r\n    *  *\r\n********\r\n    *   \r\n    *   ",
        );
    }

    #[test]
    fn examples_in_description5() {
        assert_eq!(
            execute("F1LF2LF3LF4LF5LF6LF7LF8LF9LF10LF11LF12LF13LF14LF15LF16LF17LF18LF19LF20L"),
            "********************\r\n*                  *\r\n* **************** *\r\n* *              * *\r\n* * ************ * *\r\n* * *          * * *\r\n* * * ******** * * *\r\n* * * *      * * * *\r\n* * * * **** * * * *\r\n* * * * *  * * * * *\r\n* * * * * ** * * * *\r\n* * * * *    * * * *\r\n* * * * ****** * * *\r\n* * * *        * * *\r\n* * * ********** * *\r\n* * *            * *\r\n* * ************** *\r\n* *                *\r\n* ******************\r\n*                   \r\n*                   ",
        );
    }
}
