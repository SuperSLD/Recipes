package online.jutter.supersld

/**
 * Стейт машина для контроля состояний какого-либо объекта.
 *
 * @param T тип определяющий состояние. (волшебная кастомизация)
 *
 * @author Solyanoy Leonid (solyanoy.leonid@gmail.com)
 */
@Suppress("LeakingThis")
abstract class JTStateMachine<T> {

    private var onStateChangeLambda = {_:T->}
    private var stateMap: HashMap<T, List<T>>? = null
    private var currentState: T? = null

    init {
        currentState = this.setStartState()
        stateMap = this.initStateMap()
    }

    /**
     * Инициализация стейт карты.
     *
     * <p>При реализации класса необходимо определить карту
     * состояний, сообщаяющую из какого состояния к какому мы
     * можем совершить переход</p>
     *
     * <p>Например при трех состояниях карта состояний
     * будет выглядеть примерно так:</p>
     *
     * <p>START_STATE -> PLAY_MUSIC, PLAY_VIDEO
     * PLAY_VIDEO -> START_STATE</p>
     *
     * <p>Мы говорим от какого состояния к какому возможно
     * совершить переход. При попытке совершить не описанный
     * переход будет падать ошибка {@link #StateIllegalTransaction}.</p>
     *
     * @return карта переходов.
     */
    abstract fun initStateMap(): HashMap<T, List<T>>

    /**
     * Определение стартового положения от которого.
     *
     * @return стартовое состояние стейт машины.
     */
    abstract fun setStartState(): T

    /**
     * Переход к новому состоянию из текущего,
     * если переход описан в карте.
     */
    fun goToState(newState: T) {
        if (stateMap!![currentState]!!.contains(newState)) {
            currentState = newState
            onStateChangeLambda(newState)
        } else throw StateIllegalTransaction(
            "state transaction failed [$currentState -> $newState], check initialisation state map"
        )
    }

    /**
     * Устаноква калбека для прослушивания смены
     * состояния стейт машины.
     *
     * @param callback лямбда которая вызовется при смене состояния.
     */
    fun onStateChange(callback: (T) -> Unit) {
        this.onStateChangeLambda = callback
    }

    /**
     * Плучение текущего состояня стейт машины.
     *
     * @return текущее состояние стейт машины.
     */
    fun getCurrentState() = currentState

    /**
     * Сообщение о неправильном переходе.
     */
    private class StateIllegalTransaction(override val message: String): Exception()
}