fn main() {}

fn square_area_to_circle(size:f64) -> f64 {
    std::f64::consts::PI * (size.sqrt() * 0.5).powi(2)
}


#[cfg(test)]
mod tests {
    use super::square_area_to_circle;

    fn assert_close(a:f64, b:f64, epsilon:f64) {
        assert!( (a-b).abs() < epsilon, "Expected: {}, got: {}",b,a);
    }

#[test]
fn returns_expected1() {
  assert_close(square_area_to_circle(9.0), 7.0685834705770345, 1e-8);
}

#[test]
fn returns_expected2() {
  assert_close(square_area_to_circle(20.0), 15.70796326794897, 1e-8);
}

#[test]
fn returns_expected3() {
  assert_close(square_area_to_circle(16.0), 12.566370614359172, 1e-8);
}

#[test]
fn returns_expected4() {
  assert_close(square_area_to_circle(34.0), 26.703537555513243, 1e-8);
}

#[test]
fn returns_expected5() {
  assert_close(square_area_to_circle(49.5), 38.87720908817369, 1e-8);
}
}