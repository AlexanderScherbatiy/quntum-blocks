package quantum.operator

import org.junit.Test
import quantum.blocks.Bit
import quantum.blocks.Complex.Companion.One
import quantum.blocks.Complex.Companion.Zero
import quantum.blocks.controlledFun
import quantum.junit.assertComplexEquals
import kotlin.test.assertEquals

class ControlledFunOperatorTest {

    /**
     *  controlled function
     *  |x> -> |x>
     *  |y> -> |y + f(x)>
     *
     *  f(x) = 0
     *  |x> -> |x>
     *  |y> -> |y>
     *
     *  |00> -> |00>
     *  |01> -> |01>
     *  |10> -> |10>
     *  |11> -> |11>
     *
     *  ( 1 0 0 0 )
     *  ( 0 1 0 0 )
     *  ( 0 0 1 0 )
     *  ( 0 0 0 1 )
     */
    @Test
    fun testControlledFun00() {

        val cf00 = controlledFun(2) { _ -> Bit.Zero }

        assertEquals(4, cf00.rows)
        assertEquals(4, cf00.columns)

        assertComplexEquals(One, cf00[0, 0])
        assertComplexEquals(Zero, cf00[0, 1])
        assertComplexEquals(Zero, cf00[1, 0])
        assertComplexEquals(One, cf00[1, 1])
    }

    /**
     *  controlled function
     *  |x> -> |x>
     *  |y> -> |y + f(x)>
     *
     *  f(x) = x
     *  |x> -> |x>
     *  |y> -> |y + x>
     *
     *  |00> -> |00>
     *  |01> -> |01>
     *  |10> -> |11>
     *  |11> -> |10>
     *
     *  ( 1 0 0 0 )
     *  ( 0 1 0 0 )
     *  ( 0 0 0 1 )
     *  ( 0 0 1 0 )
     */
    @Test
    fun testControlledFun01() {

        val cf01 = controlledFun(2) { bits -> bits.first() }

        assertEquals(4, cf01.rows)
        assertEquals(4, cf01.columns)

        assertComplexEquals(One, cf01[0, 0])
        assertComplexEquals(Zero, cf01[0, 1])
        assertComplexEquals(Zero, cf01[1, 0])
        assertComplexEquals(One, cf01[1, 1])

        assertComplexEquals(Zero, cf01[2, 2])
        assertComplexEquals(One, cf01[2, 3])
        assertComplexEquals(One, cf01[3, 2])
        assertComplexEquals(Zero, cf01[3, 3])
    }
}