fn main() {}

fn interpreter(code: &str, iterations: usize, width: usize, height: usize) -> String {
    let mut iter_done: usize = 0;
    let mut code_pos: usize = 0;
    let mut grid_pos: (i64, i64) = (0, 0);
    let mut grid = vec![vec![0; width]; height];
    let code_vec: Vec<char> = code.chars().collect();
    while iter_done < iterations && code_pos < code.len() {
        match code_vec[code_pos] {
            'n' => {
                grid_pos = (grid_pos.0, (grid_pos.1 - 1).rem_euclid(height as i64));
                code_pos += 1;
                iter_done += 1;
            }

            'e' => {
                grid_pos = ((grid_pos.0 + 1).rem_euclid(width as i64), grid_pos.1);
                code_pos += 1;
                iter_done += 1;
            }

            's' => {
                grid_pos = (grid_pos.0, (grid_pos.1 + 1).rem_euclid(height as i64));
                code_pos += 1;
                iter_done += 1;
            }

            'w' => {
                grid_pos = ((grid_pos.0 - 1).rem_euclid(width as i64), grid_pos.1);
                code_pos += 1;
                iter_done += 1;
            }

            '*' => {
                grid[(grid_pos.1) as usize][(grid_pos.0) as usize] =
                    (grid[(grid_pos.1) as usize][(grid_pos.0) as usize] + 1) % 2;
                code_pos += 1;
                iter_done += 1;
            }

            '[' => {
                if grid[(grid_pos.1) as usize][(grid_pos.0) as usize] == 0 {
                    let mut openings = 1;
                    while openings > 0 {
                        code_pos += 1;
                        if code_vec[code_pos] == '[' {
                            openings += 1;
                        }
                        if code_vec[code_pos] == ']' {
                            openings -= 1;
                        }
                    }
                }
                code_pos += 1;
                iter_done += 1;
            }

            ']' => {
                if grid[(grid_pos.1) as usize][(grid_pos.0) as usize] == 1 {
                    let mut closings = 1;
                    while closings > 0 {
                        code_pos -= 1;
                        if code_vec[code_pos] == ']' {
                            closings += 1;
                        }
                        if code_vec[code_pos] == '[' {
                            closings -= 1;
                        }
                    }
                }
                code_pos += 1;
                iter_done += 1;
            }

            _ => code_pos += 1,
        }
    }

    let mut result = String::new();

    for i in 0..height {
        for j in 0..width {
            result += &grid[i][j].to_string();
        }
        if i < height - 1 {
            result += "\r\n"
        }
    }

    result
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn simple_cases1() {
        assert_eq!(&interpreter("*e*e*e*es*es*ws*ws*w*w*w*n*n*n*ssss*s*s*s*", 0, 6, 9),"000000\r\n000000\r\n000000\r\n000000\r\n000000\r\n000000\r\n000000\r\n000000\r\n000000");
    }

    #[test]
    fn simple_cases2() {
        assert_eq!(&interpreter("*e*e*e*es*es*ws*ws*w*w*w*n*n*n*ssss*s*s*s*", 7, 6, 9),"111100\r\n000000\r\n000000\r\n000000\r\n000000\r\n000000\r\n000000\r\n000000\r\n000000");
    }

    #[test]
    fn simple_cases3() {
        assert_eq!(&interpreter("*e*e*e*es*es*ws*ws*w*w*w*n*n*n*ssss*s*s*s*", 19, 6, 9), "111100\r\n000010\r\n000001\r\n000010\r\n000100\r\n000000\r\n000000\r\n000000\r\n000000");
    }

    #[test]
    fn simple_cases4() {
        assert_eq!(&interpreter("*e*e*e*es*es*ws*ws*w*w*w*n*n*n*ssss*s*s*s*", 42, 6, 9), "111100\r\n100010\r\n100001\r\n100010\r\n111100\r\n100000\r\n100000\r\n100000\r\n100000");
    }

    #[test]
    fn simple_cases5() {
        assert_eq!(&interpreter("*e*e*e*es*es*ws*ws*w*w*w*n*n*n*ssss*s*s*s*", 100, 6, 9), "111100\r\n100010\r\n100001\r\n100010\r\n111100\r\n100000\r\n100000\r\n100000\r\n100000");
    }
}
